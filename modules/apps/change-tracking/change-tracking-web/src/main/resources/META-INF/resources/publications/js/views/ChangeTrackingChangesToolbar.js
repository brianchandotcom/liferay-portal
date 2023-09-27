/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayToolbar from '@clayui/toolbar';
import classNames from 'classnames';
import React, {useCallback, useRef, useState} from 'react';

import ChangeTrackingComments from '../components/ChangeTrackingComments';
import ManageCollaborators from '../components/manage-collaborators-modal/ManageCollaborators';

export default function ChangeTrackingChangesToolbar({
	collaboratorsData,
	ctMappingInfos,
	currentUserId,
	deleteCTCommentURL,
	description,
	dropdownItems,
	expired,
	getCTCommentsURL,
	name,
	namespace,
	publishURL,
	rescheduleURL,
	revertURL,
	scheduleURL,
	spritemap,
	statusLabel,
	statusStyle,
	total,
	unscheduleURL,
	updateCTCommentURL,
}) {
	const commentsCacheRef = useRef({});
	const [showComments, setShowComments] = useState(false);

	const setParameter = useCallback(
		(url, name, value) => {
			return (
				url + '&' + namespace + name + '=' + encodeURIComponent(value)
			);
		},
		[namespace]
	);

	const renderToolbarAction = (displayType, label, symbol, url) => {
		if (!url) {
			return '';
		}

		return (
			<ClayToolbar.Item>
				<a
					className={classNames(
						'btn btn-' + displayType + ' btn-sm',
						{
							disabled:
								(!total && !ctMappingInfos.length) || expired,
						}
					)}
					href={setParameter(
						url,
						'redirect',
						window.location.pathname + window.location.search
					)}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon spritemap={spritemap} symbol={symbol} />
					</span>

					{label}
				</a>
			</ClayToolbar.Item>
		);
	};

	const renderPublicationsToolbar = () => {
		return (
			<ClayToolbar className="publications-tbar" light>
				<div className="container-fluid container-fluid-max-xl">
					<ClayToolbar.Nav>
						<ClayToolbar.Item className="text-left" expand>
							<ClayToolbar.Section>
								<div className="publication-name">
									<span>{name}</span>

									<ClayLabel
										displayType={statusStyle}
										spritemap={spritemap}
									>
										{statusLabel}
									</ClayLabel>
								</div>

								<div className="publication-description">
									{description}
								</div>
							</ClayToolbar.Section>
						</ClayToolbar.Item>

						<ClayToolbar.Item>
							<ManageCollaborators {...collaboratorsData} />
						</ClayToolbar.Item>

						{renderToolbarAction(
							'secondary',
							Liferay.Language.get('schedule'),
							'calendar',
							scheduleURL
						)}

						{renderToolbarAction(
							'primary',
							Liferay.Language.get('publish'),
							'change',
							publishURL
						)}

						{renderToolbarAction(
							'secondary',
							Liferay.Language.get('unschedule'),
							'times-circle',
							unscheduleURL
						)}

						{renderToolbarAction(
							'primary',
							Liferay.Language.get('reschedule'),
							'calendar',
							rescheduleURL
						)}

						{renderToolbarAction(
							'secondary',
							Liferay.Language.get('revert'),
							'undo',
							revertURL
						)}

						<ClayToolbar.Item
							data-tooltip-align="top"
							title={Liferay.Language.get('comments')}
						>
							<ClayButton
								className={classNames(
									'nav-link nav-link-monospaced',
									{
										active: showComments,
									}
								)}
								displayType="unstyled"
								onClick={() => setShowComments(!showComments)}
							>
								<ClayIcon
									spritemap={spritemap}
									symbol="comments"
								/>
							</ClayButton>
						</ClayToolbar.Item>

						{dropdownItems && !!dropdownItems.length && (
							<ClayToolbar.Item>
								<ClayDropDownWithItems
									items={dropdownItems}
									spritemap={spritemap}
									trigger={
										<ClayButtonWithIcon
											displayType="unstyled"
											small
											spritemap={spritemap}
											symbol="ellipsis-v"
										/>
									}
								/>
							</ClayToolbar.Item>
						)}
					</ClayToolbar.Nav>
				</div>
			</ClayToolbar>
		);
	};

	return (
		<>
			{renderPublicationsToolbar()}
			<div
				className={classNames('sidenav-container sidenav-right', {
					closed: !showComments,
					open: showComments,
				})}
			>
				<div
					className="info-panel sidenav-menu-slider"
					style={
						showComments
							? {
									'height': '85vh',
									'min-height': '485px',
									'width': '320px',
							  }
							: {}
					}
				>
					<div
						className="sidebar sidebar-light sidenav-menu"
						style={
							showComments
								? {
										'height': '100%',
										'min-height': '485px',
										'width': '320px',
								  }
								: {}
						}
					>
						{showComments && (
							<ChangeTrackingComments
								ctEntryId={0}
								currentUserId={currentUserId}
								deleteCommentURL={deleteCTCommentURL}
								getCache={() => {
									return commentsCacheRef.current['0'];
								}}
								getCommentsURL={getCTCommentsURL}
								keyParam=""
								setShowComments={setShowComments}
								spritemap={spritemap}
								updateCache={(data) => {
									const cacheData = JSON.parse(
										JSON.stringify(data)
									);

									cacheData.updatedCommentId = null;

									commentsCacheRef.current['0'] = cacheData;
								}}
								updateCommentURL={updateCTCommentURL}
							/>
						)}
					</div>
				</div>
			</div>
		</>
	);
}
