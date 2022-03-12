/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAlert from '@clayui/alert';
import {useMemo, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Button, ButtonDropDown} from '../../../../../../common/components';
import {useApplicationProvider} from '../../../../../../common/context/AppPropertiesProvider';
import {PAGE_TYPES} from '../../../../utils/constants';
import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants/alertDownloadType';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertAggregateKeysDownloadText';
import {DOWNLOADABLE_LICENSE_KEYS} from '../../utils/constants/downlodableLicenseKeys';
import {getActivationKeyDownload} from '../../utils/getActivationKeyDownload';
import {getActivationKeysActionsItems} from '../../utils/getActivationKeysActionsItems';
import {getActivationKeysDownloadItems} from '../../utils/getActivationKeysDownloadItems';
import DeactivateButton from '../Deactivate';
import DownloadAlert from '../DownloadAlert';

import Filter from '../Filter';

const dxpNewRedirectLink = PAGE_TYPES.dxpNew.split('_')[1];

const DXPActivationKeysTableHeader = ({
	activationKeys,
	activationKeysFilteredState,
	activationKeysIdChecked,
	project,
	sessionId,
	setActivationKeys,
	setFilterTerm,
}) => {
	const navigate = useNavigate();
	const {licenseKeyDownloadURL} = useApplicationProvider();
	const [activationKeysFiltered] = activationKeysFilteredState;

	const [status, setStatus] = useState({
		deactivate: '',
		download: '',
	});

	const selectedActivationKeysIDs = useMemo(
		() =>
			activationKeysIdChecked.reduce(
				(selectedKeysIDAccumulator, activationKeyIdChecked, index) =>
					`${selectedKeysIDAccumulator}${
						index > 0 ? '&' : ''
					}licenseKeyIds=${activationKeyIdChecked}`,
				''
			),
		[activationKeysIdChecked]
	);

	const activationKeysChecked = useMemo(
		() =>
			activationKeysFiltered.filter(({id}) =>
				activationKeysIdChecked.includes(id)
			),
		[activationKeysFiltered, activationKeysIdChecked]
	);

	const isAbleToDownloadAggregateKeys = useMemo(() => {
		const [
			firstActivationKeyChecked,
			...restActivationKeysChecked
		] = activationKeysChecked;

		return restActivationKeysChecked.every(
			(selectedKey) =>
				DOWNLOADABLE_LICENSE_KEYS.above71DXPVersion(
					firstActivationKeyChecked,
					selectedKey
				) ||
				DOWNLOADABLE_LICENSE_KEYS.below71DXPVersion(
					firstActivationKeyChecked,
					selectedKey
				)
		);
	}, [activationKeysChecked]);

	const handleAlertStatus = (hasSuccessfullyDownloadedKeys) =>
		setStatus((previousStatus) => ({
			...previousStatus,
			download: hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger,
		}));

	const handleRedirectPage = () => navigate(dxpNewRedirectLink);
	const activationKeysActionsItems = getActivationKeysActionsItems(
		project?.accountKey,
		licenseKeyDownloadURL,
		sessionId,
		handleAlertStatus,
		handleRedirectPage
	);

	const activationKeysDownloadItems = getActivationKeysDownloadItems(
		isAbleToDownloadAggregateKeys,
		selectedActivationKeysIDs,
		licenseKeyDownloadURL,
		sessionId,
		handleAlertStatus,
		activationKeysChecked,
		project.name
	);

	const getCurrentButton = () => {
		if (activationKeysIdChecked?.length > 1) {
			return (
				<ButtonDropDown
					items={activationKeysDownloadItems}
					label="Download"
					menuElementAttrs={{
						className: 'p-0',
					}}
				/>
			);
		}

		if (activationKeysIdChecked.length) {
			return (
				<Button
					className="btn btn-primary"
					onClick={async () =>
						getActivationKeyDownload(
							activationKeysIdChecked,
							licenseKeyDownloadURL,
							sessionId,
							handleAlertStatus,
							activationKeysChecked[0],
							project.name
						)
					}
				>
					Download
				</Button>
			);
		}

		return (
			<ButtonDropDown
				items={activationKeysActionsItems}
				label="Actions"
				menuElementAttrs={{
					className: 'p-0',
				}}
			/>
		);
	};

	return (
		<div>
			<div className="align-items-center bg-neutral-1 d-flex mb-2 p-3 rounded">
				<Filter
					activationKeys={activationKeys}
					setFilterTerm={setFilterTerm}
				/>

				<div className="align-items-center d-flex ml-auto">
					{!!activationKeysIdChecked.length && (
						<>
							<p className="font-weight-semi-bold m-0 ml-auto text-neutral-10">
								{`${activationKeysIdChecked.length} Keys Selected`}
							</p>

							<DeactivateButton
								deactivateKeysStatus={status.deactivate}
								selectedKeys={activationKeysIdChecked}
								sessionId={sessionId}
								setActivationKeys={setActivationKeys}
								setDeactivateKeysStatus={(value) =>
									setStatus((previousStatus) => ({
										...previousStatus,
										deactivate: value,
									}))
								}
							/>
						</>
					)}

					{getCurrentButton()}
				</div>
			</div>

			{status.download && (
				<DownloadAlert
					downloadStatus={status.download}
					message={
						ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT[
							status.download
						]
					}
					setDownloadStatus={(value) =>
						setStatus((previousStatus) => ({
							...previousStatus,
							download: value,
						}))
					}
				/>
			)}

			{status.deactivate === ALERT_DOWNLOAD_TYPE.success && (
				<DownloadAlert
					downloadStatus="success"
					message="Activation Key(s) were deactivated successfully."
					setDownloadStatus={(value) =>
						setStatus((previousStatus) => ({
							...previousStatus,
							deactivate: value,
						}))
					}
				/>
			)}

			{!isAbleToDownloadAggregateKeys && (
				<ClayAlert displayType="info">
					To download an aggregate key, select keys with identical
					<b>Type, Start Date, End Date,</b>
					and
					<b>Instance Size</b>
				</ClayAlert>
			)}
		</div>
	);
};

export default DXPActivationKeysTableHeader;
