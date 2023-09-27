/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {MouseEventHandler} from 'react';

import './DefaultPageHeader.scss';

const DefaultPageHeader: React.FC<IProps> = ({
	description,
	hideBackButton,
	onClickBack,
	portletNamespace,
	title,
}) => {
	return (
		<>
			{(onClickBack || hideBackButton) && (
				<ClayButton
					className={classNames(
						'lfr-ddm__default-page-header-back-button',
						{
							hide: hideBackButton,
						}
					)}
					displayType="link"
					onClick={onClickBack}
				>
					<ClayIcon symbol="order-arrow-left" />

					{Liferay.Language.get('back')}
				</ClayButton>
			)}
			<div
				className="lfr-ddm__default-page-header"
				id={`${portletNamespace}header`}
			>
				<div className="lfr-ddm__default-page-header-title">
					{title}
				</div>

				{description && (
					<span className="lfr-ddm__default-page-header-description">
						{description}
					</span>
				)}

				<div className="lfr-ddm__default-page-header-line" />
			</div>
		</>
	);
};

export default DefaultPageHeader;

interface IProps {
	description?: string;
	hideBackButton?: boolean;
	onClickBack?: MouseEventHandler<HTMLButtonElement>;
	portletNamespace?: string;
	title: string;
}
