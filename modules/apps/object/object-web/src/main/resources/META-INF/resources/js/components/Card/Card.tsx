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

import classNames from 'classnames';
import React from 'react';

import './Card.scss';

const Card: React.FC<React.HTMLAttributes<HTMLElement>> & {
	Body: React.FC<ICardBodyProps>;
	Header: React.FC<ICardHeaderProps>;
} = ({children, className, ...otherProps}) => {
	return (
		<div
			{...otherProps}
			className={classNames(className, 'object-admin-card')}
		>
			{children}
		</div>
	);
};

interface ICardBodyProps extends React.HTMLAttributes<HTMLElement> {}

const CardBody: React.FC<ICardBodyProps> = ({children, className}) => {
	return (
		<div className={classNames(className, 'object-admin-card__body')}>
			{children}
		</div>
	);
};

interface ICardHeaderProps extends React.HTMLAttributes<HTMLElement> {
	title: string;
}

const CardHeader: React.FC<ICardHeaderProps> = ({title}) => {
	return (
		<div className="object-admin-card__header">
			<h3 className="object-admin-card__title">{title}</h3>
		</div>
	);
};

Card.Body = CardBody;
Card.Header = CardHeader;

export default Card;
