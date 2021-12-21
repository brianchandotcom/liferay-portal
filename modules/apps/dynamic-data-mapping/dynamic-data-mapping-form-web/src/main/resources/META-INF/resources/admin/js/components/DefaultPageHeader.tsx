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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {MouseEventHandler} from 'react';

import './DefaultPageHeader.scss';

const DefaultPageHeader: React.FC<IProps> = ({
	description,
	hideBackButton,
	onClickBack,
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
			<div className="lfr-ddm__default-page-header">
				<h1 className="lfr-ddm__default-page-header-title">{title}</h1>

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
	title: string;
}
