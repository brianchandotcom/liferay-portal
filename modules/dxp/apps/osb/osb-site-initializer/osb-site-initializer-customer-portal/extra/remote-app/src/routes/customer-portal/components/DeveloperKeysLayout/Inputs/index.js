import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import client from '../../../../../apolloClient';
import BaseButton from '../../../../../common/components/BaseButton';
import {getListTypeDefinitions} from '../../../../../common/services/liferay/graphql/queries';

const arrayDXPVersion = [
	{
		key: '7.0',
		name: '7.0',
	},
	{
		key: '7.1',
		name: '7.1',
	},
	{
		key: '7.2',
		name: '7.2',
	},
	{
		key: '7.3',
		name: '7.3',
	},
	{
		key: '7.4',
		name: '7.4',
	},
];

const DevelopersKeysInputs = ({accountKey, dxpVersion}) => {
	const [dxpVersions, setDxpVersions] = useState(arrayDXPVersion);

	const [selectedVersion, setSelectedVersion] = useState(
		dxpVersion || dxpVersions[0].name
	);

	useEffect(() => {
		const fetchListTypeDefinitions = async () => {
			const {data} = await client.query({
				query: getListTypeDefinitions,
				variables: {
					filter: `name eq 'DXP Version'`,
				},
			});

			if (!data) {
				const items = data?.ListTypeDefinitions?.items?.listTypeEntres;
				setDxpVersions(
					data?.ListTypeDefinitions?.items?.listTypeEntres
				);

				setSelectedVersion(
					items.find((item) => item.name === dxpVersion).name ||
						items[items.lenght - 1].name
				);
			}
		};

		fetchListTypeDefinitions();
	}, [dxpVersion]);

	const downloadLink = `https://webserver-lrprovisioning-uat.lfr.cloud/o/provisioning-rest/v1.0/accounts/${accountKey}/product-groups/DXP/product-version/${selectedVersion}/development-license-key`;

	return (
		<div>
			<p className="text-neutral-7 text-paragraph">
				Select the Liferay DXP version for which you want to download a
				developer key.
			</p>

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

						<ClaySelect
							className="developer-keys-select font-weight-bold"
							onChange={({target}) => {
								setSelectedVersion(target.value);
							}}
						>
							{dxpVersions.map((version) => (
								<ClaySelect.Option
									className="font-weight-bold options"
									key={version.key}
									label={version.name}
								/>
							))}
						</ClaySelect>
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
