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

type FooterProps = {
	isModal?: boolean;
	onClose: () => void;
	onSubmit: () => void;
	primaryButtonTitle?: string;
};

const Footer: React.FC<FooterProps> = ({
	isModal = false,
	onClose,
	onSubmit,
	primaryButtonTitle = 'save',
}) => (
	<ClayButton.Group spaced>
		<ClayButton displayType="primary" onClick={onSubmit}>
			{i18n.translate(primaryButtonTitle)}
		</ClayButton>

		<ClayButton displayType="secondary" onClick={() => onClose()}>
			{isModal ? i18n.translate('close') : i18n.translate('cancel')}
		</ClayButton>
	</ClayButton.Group>
);

export default Footer;
