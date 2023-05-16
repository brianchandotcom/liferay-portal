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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';

import i18n from '../../../i18n';

type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
	loading?: boolean;
};

type FooterProps = {
	onClose: () => void;
	onSubmit: () => void;
	primaryButtonProps?: ButtonProps;
	secondaryButtonProps?: ButtonProps;
};

const Footer: React.FC<FooterProps> = ({
	onClose,
	onSubmit,
	primaryButtonProps: {loading, ...primaryButtonProps} = {},
	secondaryButtonProps,
}) => (
	<ClayButton.Group spaced>
		<ClayButton
			{...primaryButtonProps}
			className={classNames(
				primaryButtonProps.className,
				'align-items-center d-flex'
			)}
			disabled={primaryButtonProps?.disabled || loading}
			displayType="primary"
			onClick={onSubmit}
		>
			{loading && <ClayLoadingIndicator className="mb-0 mr-2 mt-0" />}

			{i18n.translate(primaryButtonProps?.title ?? 'save')}
		</ClayButton>

		<ClayButton
			{...secondaryButtonProps}
			displayType="secondary"
			onClick={() => onClose()}
		>
			{i18n.translate(secondaryButtonProps?.title ?? 'cancel')}
		</ClayButton>
	</ClayButton.Group>
);

export default Footer;
