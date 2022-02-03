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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useState} from 'react';
import {Button, ButtonDropDown} from '../../../../../common/components';

export const ACTIVATION_KEYS_ACTIONS_ITEMS = [
	{
		icon: <ClayIcon className="mr-1 text-neutral-4" symbol="download" />,
		label: 'Export All Key Details (csv)',
		onClick: () => {},
	},
];

export const ACTIVATION_KEYS_DOWNLOAD_ITEMS = [
	{
		icon: <ClayIcon className="mr-1 text-neutral-4" symbol="document" />,
		label: 'Aggregate Key (single file)',
		onClick: () => {},
	},
];

const DXPActivationKeysTableHeader = ({selectedKeys}) => {
	const [active, setActive] = useState(false);

	const currentButton = () => {
		if (selectedKeys.length > 1) {
			return (
				<ButtonDropDown
					active={active}
					items={ACTIVATION_KEYS_DOWNLOAD_ITEMS}
					label="Download"
					menuElementAttrs={{
						className: 'p-0',
					}}
					setActive={setActive}
				/>
			);
		}

		if (selectedKeys.length) {
			return <Button className="btn btn-primary">Download</Button>;
		}

		return (
			<ButtonDropDown
				active={active}
				items={ACTIVATION_KEYS_ACTIONS_ITEMS}
				label="Actions"
				menuElementAttrs={{
					className: 'p-0',
				}}
				setActive={setActive}
			/>
		);
	};

	return (
		<div className="align-items-center bg-neutral-1 d-flex p-3 rounded">
			{!!selectedKeys.length && (
				<>
					<p className="font-weight-semi-bold m-0 ml-auto text-neutral-10">
						{`${selectedKeys.length} Keys Selected`}
					</p>

					<Button className="btn-outline-danger cp-deactivate-button mx-2">
						Deactivate
					</Button>
				</>
			)}

			<div
				className={classNames({
					'ml-auto': !selectedKeys.length,
				})}
			>
				{currentButton()}
			</div>
		</div>
	);
};

export default DXPActivationKeysTableHeader;
