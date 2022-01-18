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
import {getListTypeDefinitions} from '../../../../../common/services/liferay/graphql/queries';

const TEXT = {
	'dxp':
		'Select the Liferay DXP version for which you want to download a developer key.',
	'dxp-cloud':
		'To activate a local instance of Liferay DXP, download a developer key for your Liferay DXP version.',
};

const DevelopersKeysInputs = ({accountKey, dxpVersion, page}) => {
	const [dxpVersions, setDxpVersions] = useState([]);

	const [selectedVersion, setSelectedVersion] = useState(dxpVersion);

	const downloadText = TEXT[page];

	useEffect(() => {
		const fetchListTypeDefinitions = async () => {
			const {data} = await client.query({
				query: getListTypeDefinitions,
				variables: {
					filter: `name eq 'DXP Version'`,
				},
			});

			if (data) {
				const items = data.listTypeDefinitions.items[0].listTypeEntries;
				const orderedItems = [...items].sort((a, b) => b.name - a.name);

				setDxpVersions(orderedItems);
				// eslint-disable-next-line no-console
				console.log(orderedItems[0].name);

				setSelectedVersion(
					orderedItems.find((item) => item.name === dxpVersion)
						?.name || orderedItems[0].name
				);
			}
		};

		fetchListTypeDefinitions();
	}, [dxpVersion]);

	const downloadLink = `https://webserver-lrprovisioning-uat.lfr.cloud/o/provisioning-rest/v1.0/accounts/${accountKey}/product-groups/DXP/product-version/${selectedVersion}/development-license-key`;

	return (
		<div>
			<p className="text-neutral-7 text-paragraph">{downloadText}</p>

			<div className="align-items-start d-flex">
				<label
					className="developer-keys-label mb-3 mr-3"
					id="subscription-select"
				>
					<div className="position-relative">
						<ClayIcon
							className="select-icon"
							symbol="caret-bottom"
						/>

						{selectedVersion && (
							<ClaySelect
								className="developer-keys-select font-weight-bold"
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

				<BaseButton className="btn btn-outline-primary">
					<a
						download
						href={downloadLink}
						rel="noreferrer noopener"
						target="_blank"
					>
						<ClayIcon className="mr-1" symbol="download" />
						Download Key
					</a>
				</BaseButton>
			</div>

			<p className="text-neutral-7">
				For instructions on how to activate your Liferay DXP or Liferay
				Portal instance, please read the
				<a
					href="https://help.liferay.com/hc/en-us/articles/360018163571-Deploying-Activation-Keys "
					rel="noreferrer noopener"
					target="_blank"
				>
					<u className="font-weight-semi-bold text-neutral-7">
						{' '}
						Deploying Activation Keys article.
					</u>
				</a>
			</p>
		</div>
	);
};

export default DevelopersKeysInputs;
