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

import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import client from '../../../../../apolloClient';
import BaseButton from '../../../../../common/components/BaseButton';
import {useApplicationProvider} from '../../../../../common/context/ApplicationPropertiesProvider';
import {getListTypeDefinitions} from '../../../../../common/services/liferay/graphql/queries';
import {fetchDeveloperKeysLicense} from '../../../../../common/services/liferay/raysource-api';
import {downloadFromBlob} from '../../../../../common/utils';
import {
	EXTENSIONS_FILE_TYPE,
	LIST_TYPES,
	STATUS_CODE,
} from '../../../utils/constants';

const DevelopersKeysInputs = ({
	accountKey,
	downloadTextHelper,
	dxpVersion,
	productTitle,
	sessionId,
}) => {
	const {
		deployingActivationKeysURL,
		licenseKeyDownloadURL,
	} = useApplicationProvider();
	const [dxpVersions, setDxpVersions] = useState([]);
	const [selectedVersion, setSelectedVersion] = useState(dxpVersion);

	useEffect(() => {
		const fetchListTypeDefinitions = async () => {
			const {data} = await client.query({
				query: getListTypeDefinitions,
				variables: {
					filter: `name eq '${LIST_TYPES.dxpVersion}'`,
				},
			});

			if (data) {
				const items = data.listTypeDefinitions.items[0].listTypeEntries;
				const orderedItems = [...items].sort((a, b) => b.name - a.name);

				setDxpVersions(orderedItems);

				setSelectedVersion(
					orderedItems.find((item) => item.name === dxpVersion)
						?.name || orderedItems[0]?.name
				);
			}
		};

		fetchListTypeDefinitions();
	}, [dxpVersion]);

	const handleClick = async () => {
		const license = await fetchDeveloperKeysLicense(
			accountKey,
			encodeURI(productTitle),
			licenseKeyDownloadURL,
			sessionId,
			selectedVersion
		);

		if (license.status === STATUS_CODE.SUCCESS) {
			const contentType = license.headers.get('content-type');
			const extensionFile = EXTENSIONS_FILE_TYPE[contentType] || '.txt';
			const licenseBlob = await license.blob();

			return downloadFromBlob(licenseBlob, `license${extensionFile}`);
		}
	};

	return (
		<div>
			<p className="text-neutral-7 text-paragraph">
				{downloadTextHelper}
			</p>

			<div className="align-items-baseline d-flex">
				<label className="developer-keys-label mb-3 mr-3">
					<div className="position-relative">
						<ClayIcon
							className="select-icon"
							symbol="caret-bottom"
						/>

						{selectedVersion && (
							<ClaySelect
								className="bg-neutral-1 border-0 font-weight-bold mr-2 pr-6"
								onChange={({target}) => {
									setSelectedVersion(target.value);
								}}
								value={selectedVersion}
							>
								{selectedVersion &&
									dxpVersions.map((version) => (
										<ClaySelect.Option
											className="font-weight-bold options"
											key={version.key}
											label={version.name}
										/>
									))}
							</ClaySelect>
						)}
					</div>
				</label>

				<BaseButton
					className="btn btn-outline-primary developer-keys-button py-1"
					onClick={handleClick}
					prependIcon="download"
					type="button"
				>
					Download Key
				</BaseButton>
			</div>

			<p className="text-neutral-7">
				{`For instructions on how to activate your Liferay DXP or Liferay
				Portal instance, please read the `}

				<a
					href={deployingActivationKeysURL}
					rel="noreferrer noopener"
					target="_blank"
				>
					<u className="font-weight-semi-bold text-neutral-7">
						Deploying Activation Keys article.
					</u>
				</a>
			</p>
		</div>
	);
};

export default DevelopersKeysInputs;
