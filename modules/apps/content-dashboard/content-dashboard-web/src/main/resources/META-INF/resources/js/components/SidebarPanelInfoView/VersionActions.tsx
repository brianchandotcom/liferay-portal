/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';

const VersionActions = ({actions}: IProps) => {
	const handleActionClick = ({url}: {url: string}): void => {
		window.submitForm((document as IDocument).hrefFm, url);
	};

	return (
		<ClayDropDown
			className="align-self-start pt-2"
			closeOnClick
			data-tooltip-align="left"
			title={Liferay.Language.get('actions')}
			trigger={
				<ClayButtonWithIcon
					borderless
					displayType="secondary"
					symbol="ellipsis-v"
				/>
			}
		>
			<ClayDropDown.ItemList>
				{actions.map(({icon, label, name, url}) => (
					<ClayDropDown.Item
						key={name}
						onClick={() => handleActionClick({url})}
					>
						<ClayIcon symbol={icon || ''} />

						<span className="pl-3">{label}</span>
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

interface IProps {
	actions: IAction[];
}

export interface IAction {
	icon?: string;
	label: string;
	name: string;
	url: string;
}

declare global {
	interface Window {
		submitForm: (form: HTMLElement, url: string) => void;
	}

	interface IDocument extends Document {
		hrefFm: HTMLElement;
	}
}

export default VersionActions;
