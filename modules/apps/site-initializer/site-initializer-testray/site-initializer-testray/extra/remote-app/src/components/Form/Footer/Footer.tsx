/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
