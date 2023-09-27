/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Navigate, useOutletContext} from 'react-router-dom';
import {useCustomerPortal} from '../../context';
import {hasAdminOrPartnerManager} from '../ActivationKeysTable/utils/hasAdminOrPartnerManager';
import GenerateNewKeySkeleton from './Skeleton';
import ComplimentaryDate from './pages/ComplimentaryDate';
import RequiredInformation from './pages/RequiredInformation';
import SelectSubscription from './pages/SelectSubscription';
import {STEP_TYPES} from './utils/constants/stepType';

const ACTIVATION_ROOT_ROUTER = 'activation';

const GenerateNewKey = ({productGroupName}) => {
	const [{project, sessionId, userAccount}] = useCustomerPortal();
	const [infoSelectedKey, setInfoSelectedKey] = useState();
	const [step, setStep] = useState(STEP_TYPES.selectDescriptions);
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();

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

	const StepLayout = {
		[STEP_TYPES.generateKeys]: (
			<RequiredInformation
				accountKey={project?.accountKey}
				infoSelectedKey={infoSelectedKey}
				sessionId={sessionId}
				setStep={setStep}
				urlPreviousPage={urlPreviousPage}
			/>
		),
		[STEP_TYPES.selectDescriptions]: (
			<SelectSubscription
				accountKey={project?.accountKey}
				infoSelectedKey={infoSelectedKey}
				productGroupName={productGroupName}
				sessionId={sessionId}
				setInfoSelectedKey={setInfoSelectedKey}
				setStep={setStep}
				urlPreviousPage={urlPreviousPage}
			/>
		),
		[STEP_TYPES.selectInfoComplementaryKey]: (
			<ComplimentaryDate
				accountKey={project?.accountKey}
				infoSelectedKey={infoSelectedKey}
				productGroupName={productGroupName}
				sessionId={sessionId}
				setInfoSelectedKey={setInfoSelectedKey}
				setStep={setStep}
				urlPreviousPage={urlPreviousPage}
			/>
		),
	};

	return StepLayout[step];
};

GenerateNewKey.Skeleton = GenerateNewKeySkeleton;

export default GenerateNewKey;
