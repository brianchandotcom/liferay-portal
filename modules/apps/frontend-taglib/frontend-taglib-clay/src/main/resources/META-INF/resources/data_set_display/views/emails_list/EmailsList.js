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

import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import ActionsDropdownRenderer from '../../data_renderers/ActionsDropdownRenderer';

function Email({
	actionItems,
	author,
	borderBottom,
	dataSetDisplayContext,
	date,
	href,
	status,
	subject,
	summary,
}) {
	const {openSidePanel} = useContext(dataSetDisplayContext);

	function handleClickOnSubject(event) {
		event.preventDefault();

		openSidePanel({
			slug: 'email',
			url: href,
		});
	}

	return (
		<li
			className={classNames(
				'bg-white d-flex p-4',
				borderBottom
					? 'border-top-0 border-left-0 border-right-0 border-bottom'
					: 'border-0'
			)}
		>
			<div className="row">
				<div className="col">
					<div className="row">
						<div className="col">
							<div className="row">
								{author.avatarSrc && (
									<div className="col-auto">
										<ClaySticker
											className="sticker-user-icon"
											size="xl"
										>
											<div className="sticker-overlay">
												<img
													className="sticker-img"
													src={author.avatarSrc}
												/>
											</div>
										</ClaySticker>
									</div>
								)}
								<div className="col d-flex flex-column justify-content-center">
									<small className="d-block text-body">
										<strong>{author.name}</strong>
									</small>
									<small className="d-block">
										{author.email}
									</small>
								</div>
							</div>
						</div>
						<div className="col-auto d-flex flex-column justify-content-center">
							<ClayLabel
								displayType={status.displayStyle || 'success'}
							>
								{status.label}
							</ClayLabel>
						</div>
						<div className="col-auto d-flex flex-column justify-content-center">
							<small>{date}</small>
						</div>
						<div className="col-12">
							<h5 className="mt-3">
								<a href="#" onClick={handleClickOnSubject}>
									{subject}
								</a>
							</h5>
							<div>{summary}</div>
						</div>
					</div>
				</div>
				{actionItems.length ? (
					<div className="col-auto d-flex flex-column justify-content-center">
						<ActionsDropdownRenderer actions={actionItems} />
					</div>
				) : null}
			</div>
		</li>
	);
}

Email.propTypes = {
	actionItems: PropTypes.array,
	author: PropTypes.shape({
		avatarSrc: PropTypes.string,
		email: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
	}).isRequired,
	borderBottom: PropTypes.bool,
	date: PropTypes.string.isRequired,
	href: PropTypes.string,
	status: PropTypes.shape({
		displayStyle: PropTypes.string,
		label: PropTypes.string.isRequired,
	}),
	subject: PropTypes.string.isRequired,
	summary: PropTypes.string.isRequired,
};

Email.defaultProps = {
	actionItems: [],
};

function EmailsList({dataSetDisplayContext, items}) {
	const {style} = useContext(dataSetDisplayContext);

	return (
		<ClayList
			className={classNames(
				'mb-0',
				style === 'default' ? 'border-bottom' : 'border'
			)}
		>
			{items.map((item, i) => (
				<Email
					key={i}
					{...item}
					borderBottom={i !== items.length - 1}
					dataSetDisplayContext={dataSetDisplayContext}
				/>
			))}
		</ClayList>
	);
}

EmailsList.propTypes = {
	dataRenderers: PropTypes.object,
	dataSetDisplayContext: PropTypes.any,
	items: PropTypes.array,
};

EmailsList.defaultProps = {
	items: [],
};

export default EmailsList;
