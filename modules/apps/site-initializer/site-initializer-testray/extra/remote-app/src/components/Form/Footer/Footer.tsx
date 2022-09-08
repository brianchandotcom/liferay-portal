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

import ClayButton from '@clayui/button';

import i18n from '../../../i18n';

type ButtonProps = {
	title: string;
} & React.ButtonHTMLAttributes<HTMLButtonElement>;

type FooterProps = {
	isModal?: boolean;
	onClose: () => void;
	onSubmit: () => void;
	primaryButtonProps?: ButtonProps;
	secondaryButtonProps?: ButtonProps;
};

const Footer: React.FC<FooterProps> = ({
	onClose,
	onSubmit,
	primaryButtonProps = {
		title: i18n.translate('save'),
	},
	secondaryButtonProps = {
		title: i18n.translate('cancel'),
	},
}) => (
	<ClayButton.Group spaced>
		<ClayButton
			{...primaryButtonProps}
			displayType="primary"
			onClick={onSubmit}
		>
			{i18n.translate(primaryButtonProps.title)}
		</ClayButton>

		<ClayButton
			{...secondaryButtonProps}
			displayType="secondary"
			onClick={() => onClose()}
		>
			{secondaryButtonProps.title}
		</ClayButton>
	</ClayButton.Group>
);

export default Footer;
