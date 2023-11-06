/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Navigate, useLocation, useOutletContext} from 'react-router-dom';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import {putDeactivateKeys} from '~/common/services/liferay/rest/raysource/LicenseKeys';
import {useCustomerPortal} from '../../context';
import {ALERT_DOWNLOAD_TYPE, STATUS_CODE} from '../../utils/constants';
import {hasAdminOrPartnerManager} from '../ActivationKeysTable/utils/hasAdminOrPartnerManager';
import GenerateNewKeySkeleton from './Skeleton';
import ComplimentaryDate from './pages/ComplimentaryDate';
import RequiredInformation from './pages/RequiredInformation';
import SelectSubscription from './pages/SelectSubscription';
import {STEP_TYPES} from './utils/constants/stepType';

const ACTIVATION_ROOT_ROUTER = 'activation';

const GenerateNewKey = ({
	hasKeyComplimentary,
	productGroupName,
	setHasKeyComplimentary,
}) => {
	const {provisioningServerAPI} = useAppPropertiesContext();
	const [{project, sessionId, userAccount}] = useCustomerPortal();
	const [infoSelectedKey, setInfoSelectedKey] = useState();
	const [step, setStep] = useState(STEP_TYPES.selectDescriptions);
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();
	const [status, setStatus] = useState({
		deactivate: '',
		downloadAggregated: '',
		downloadMultiple: '',
	});

	const [isDeactivating, setIsDeactivating] = useState(false);
	const [alreadyDeactivated, setAlreadyDeactivated] = useState(false);

	const {state} = useLocation();
	const [purposeDescription, setPurposeDescription] = useState('');

	useEffect(() => {
		setHasQuickLinksPanel(false);
		setHasSideMenu(false);
	}, [setHasSideMenu, setHasQuickLinksPanel]);

	const isAdminOrPartnerManager = hasAdminOrPartnerManager(
		project,
		userAccount
	);

	if (!isAdminOrPartnerManager) {
		return <Navigate replace={true} to={`/${project?.accountKey}`} />;
	}

	const urlPreviousPage = `/${
		project?.accountKey
	}/${ACTIVATION_ROOT_ROUTER}/${productGroupName.toLowerCase()}`;

	const deactivateKeysConfirm = async () => {
		setIsDeactivating(true);

		const response = await putDeactivateKeys(
			provisioningServerAPI,
			state.filterCheckedActivationKeys,
			sessionId
		);

		if (response.status === STATUS_CODE.successNoContent) {
			setIsDeactivating(false);
			setAlreadyDeactivated(true);

			return;
		}

		setIsDeactivating(false);
		setStatus({...status, deactivate: ALERT_DOWNLOAD_TYPE.danger});
	};

	const StepLayout = {
		[STEP_TYPES.generateKeys]: (
			<RequiredInformation
				accountKey={project?.accountKey}
				hasKeyComplimentary={hasKeyComplimentary}
				infoSelectedKey={infoSelectedKey}
				purposeDescription={purposeDescription}
				sessionId={sessionId}
				setStep={setStep}
				urlPreviousPage={urlPreviousPage}
			/>
		),
		[STEP_TYPES.selectDescriptions]: (
			<SelectSubscription
				accountKey={project?.accountKey}
				activationKeysByStatusPaginatedChecked
				alreadyDeactivated={alreadyDeactivated}
				deactivateKeysConfirm={deactivateKeysConfirm}
				filterCheckedActivationKeys
				hasKeyComplimentary={hasKeyComplimentary}
				identifier
				infoSelectedKey={infoSelectedKey}
				isDeactivating={isDeactivating}
				productGroupName={productGroupName}
				sessionId={sessionId}
				setHasKeyComplimentary={setHasKeyComplimentary}
				setInfoSelectedKey={setInfoSelectedKey}
				setStep={setStep}
				urlPreviousPage={urlPreviousPage}
			/>
		),
		[STEP_TYPES.selectInfoComplementaryKey]: (
			<ComplimentaryDate
				accountKey={project?.accountKey}
				deactivateKeysConfirm={deactivateKeysConfirm}
				deactivateKeysStatus={status.deactivate}
				filterCheckedActivationKeys
				infoSelectedKey={infoSelectedKey}
				productGroupName={productGroupName}
				purposeDescription={purposeDescription}
				sessionId={sessionId}
				setDeactivateKeysStatus={(value) =>
					setStatus((previousStatus) => ({
						...previousStatus,
						deactivate: value,
					}))
				}
				setInfoSelectedKey={setInfoSelectedKey}
				setPurposeDescription={setPurposeDescription}
				setStep={setStep}
				urlPreviousPage={urlPreviousPage}
			/>
		),
	};

	return StepLayout[step];
};

GenerateNewKey.Skeleton = GenerateNewKeySkeleton;

export default GenerateNewKey;
