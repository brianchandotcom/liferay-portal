/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import Actions from '../../actions/Actions';

function Email({
	actionDropdownItems,
	author,
	borderBottom,
	date,
	frontendDataSetContext,
	href,
	status,
	subject,
	summary,
}) {
	const {openSidePanel} = useContext(frontendDataSetContext);

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

				{actionDropdownItems.length ? (
					<div className="col-auto d-flex flex-column justify-content-center">
						<Actions actions={actionDropdownItems} />
					</div>
				) : null}
			</div>
		</li>
	);
}

Email.propTypes = {
	actionDropdownItems: PropTypes.array,
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

function EmailsList({dataLoading, frontendDataSetContext, items}) {
	const {style} = useContext(frontendDataSetContext);

	if (dataLoading) {
		return <ClayLoadingIndicator className="mt-7" />;
	}

	if (!items?.length) {
		return (
			<ClayEmptyState
				description={Liferay.Language.get(
					'sorry,-no-results-were-found'
				)}
				imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
				title={Liferay.Language.get('no-results-found')}
			/>
		);
	}

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
					frontendDataSetContext={frontendDataSetContext}
				/>
			))}
		</ClayList>
	);
}

EmailsList.propTypes = {
	frontendDataSetContext: PropTypes.any,
	items: PropTypes.array,
};

EmailsList.defaultProps = {
	items: [],
};

export default EmailsList;
