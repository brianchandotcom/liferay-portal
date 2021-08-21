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

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

const CTEditComment = ({handleCancel, handleSave, initialValue}) => {
	const [value, setValue] = useState(initialValue);

	return (
		<div className="autofit-col autofit-col-expand">
			<ClayForm.Group>
				<ClayInput
					aria-label={Liferay.Language.get('comment')}
					component="textarea"
					onChange={(event) => setValue(event.target.value)}
					placeholder={Liferay.Language.get('type-your-comment-here')}
					type="text"
					value={value}
				/>
			</ClayForm.Group>
			<div className="btn-group">
				<div className="btn-group-item">
					<button
						className={`btn btn-primary${value ? '' : ' disabled'}`}
						onClick={() => handleSave(value)}
						type="button"
					>
						{Liferay.Language.get('save')}
					</button>
				</div>
				<div className="btn-group-item">
					<button
						className="btn btn-secondary"
						onClick={() => handleCancel()}
						type="button"
					>
						{Liferay.Language.get('cancel')}
					</button>
				</div>
			</div>
		</div>
	);
};

export default ({
	ctEntryId,
	currentUserId,
	deleteCommentURL,
	getCache,
	getCommentsURL,
	keyParam,
	setShowComments,
	spritemap,
	updateCache,
	updateCommentURL,
}) => {
	const [deleting, setDeleting] = useState(0);
	const [editing, setEditing] = useState(0);
	const [fetchData, setFetchData] = useState(null);
	const [inputValue, setInputValue] = useState('');
	const [loading, setLoading] = useState(false);

	const sidebarRef = useRef(null);

	const commentRef = useCallback((node) => {
		if (node && sidebarRef.current) {
			sidebarRef.current.scrollTop = sidebarRef.current.scrollHeight;
		}
	}, []);

	useEffect(() => {
		setDeleting(0);
		setEditing(0);
		setInputValue('');

		const data = getCache();

		if (data && data.comments) {
			setFetchData(data);

			return;
		}

		setLoading(true);

		AUI().use('liferay-portlet-url', () => {
			const portletURL = Liferay.PortletURL.createURL(getCommentsURL);

			portletURL.setParameter('ctEntryId', ctEntryId.toString());

			fetch(portletURL.toString())
				.then((response) => response.json())
				.then((json) => {
					if (!json.comments) {
						setFetchData({
							errorMessage: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						});
						setLoading(false);

						return;
					}

					setFetchData(json);
					setLoading(false);

					updateCache(json);
				})
				.catch(() => {
					setFetchData({
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					});

					setLoading(false);
				});
		});
	}, [ctEntryId, getCache, getCommentsURL, updateCache]);

	const handleDelete = (ctCommentId) => {
		AUI().use('liferay-portlet-url', () => {
			const portletURL = Liferay.PortletURL.createURL(deleteCommentURL);

			portletURL.setParameter('ctCommentId', ctCommentId.toString());
			portletURL.setParameter('ctEntryId', ctEntryId.toString());

			fetch(portletURL.toString())
				.then((response) => response.json())
				.then((json) => {
					if (!json.comments) {
						setFetchData({
							errorMessage: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						});

						return;
					}

					setFetchData(json);

					updateCache(json);
				})
				.catch(() => {
					setFetchData({
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					});
				});
		});
	};

	const handleReply = () => {
		AUI().use('liferay-portlet-url', () => {
			setLoading(true);

			const portletURL = Liferay.PortletURL.createURL(updateCommentURL);

			portletURL.setParameter('ctEntryId', ctEntryId.toString());
			portletURL.setParameter('value', inputValue);

			fetch(portletURL.toString())
				.then((response) => response.json())
				.then((json) => {
					setDeleting(0);
					setEditing(0);

					if (!json.comments) {
						setFetchData({
							errorMessage: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						});
						setLoading(false);

						return;
					}

					setFetchData(json);
					setInputValue('');
					setLoading(false);

					updateCache(json);
				})
				.catch(() => {
					setDeleting(0);
					setEditing(0);
					setFetchData({
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					});
					setLoading(false);
				});
		});
	};

	const handleUpdate = (ctCommentId, newValue) => {
		AUI().use('liferay-portlet-url', () => {
			const portletURL = Liferay.PortletURL.createURL(updateCommentURL);

			portletURL.setParameter('ctCommentId', ctCommentId);
			portletURL.setParameter('ctEntryId', ctEntryId.toString());
			portletURL.setParameter('value', newValue);

			fetch(portletURL.toString())
				.then((response) => response.json())
				.then((json) => {
					setDeleting(0);
					setEditing(0);

					if (!json.comments) {
						setFetchData({
							errorMessage: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						});

						return;
					}

					setFetchData(json);
					updateCache(json);
				})
				.catch(() => {
					setDeleting(0);
					setEditing(0);
					setFetchData({
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					});
				});
		});
	};

	const renderUserPortrait = (userId) => {
		const user = fetchData.userInfo[userId.toString()];

		return (
			<ClaySticker
				className={`sticker-user-icon ${
					user.portraitURL ? '' : 'user-icon-color-' + (userId % 10)
				}`}
				data-tooltip-align="top"
				size="lg"
				title={user.userName}
			>
				{user.portraitURL ? (
					<div className="sticker-overlay">
						<img className="sticker-img" src={user.portraitURL} />
					</div>
				) : (
					<ClayIcon symbol="user" />
				)}
			</ClaySticker>
		);
	};

	const renderComments = () => {
		if (!fetchData) {
			return <span aria-hidden="true" className="loading-animation" />;
		}
		else if (!fetchData.comments || fetchData.comments.length === 0) {
			return (
				<div className="taglib-empty-result-message">
					<div className="taglib-empty-result-message-header" />
					<div className="sheet-text text-center">
						{Liferay.Language.get('no-comments-yet')}
					</div>
				</div>
			);
		}

		const items = [];

		const filteredComments = fetchData.comments.slice(0);

		filteredComments.sort((a, b) => {
			if (a.createTime < b.createTime) {
				return -1;
			}

			if (a.createTime > b.createTime) {
				return 1;
			}

			return 0;
		});

		for (let i = 0; i < filteredComments.length; i++) {
			const comment = filteredComments[i];

			let title = fetchData.userInfo[comment.userId.toString()].userName;

			if (currentUserId === comment.userId) {
				title = title + ' (' + Liferay.Language.get('you') + ')';
			}

			const dropdownItems = [
				{
					label: Liferay.Language.get('edit'),
					onClick: () => {
						setDeleting(0);
						setEditing(comment.ctCommentId);
					},
					symbolLeft: 'pencil',
				},
				{
					label: Liferay.Language.get('delete'),
					onClick: () => {
						setDeleting(comment.ctCommentId);
						setEditing(0);
					},
					symbolLeft: 'times-circle',
				},
			];

			let commentBody = <pre>{comment.value}</pre>;

			if (editing === comment.ctCommentId) {
				commentBody = (
					<CTEditComment
						handleCancel={() => setEditing(0)}
						handleSave={(saveValue) =>
							handleUpdate(comment.ctCommentId, saveValue)
						}
						initialValue={comment.value}
					/>
				);
			}

			items.push(
				<div
					className={`${
						deleting === comment.ctCommentId
							? ' comment-deleting'
							: ''
					}${
						fetchData &&
						fetchData.updatedCommentId &&
						fetchData.updatedCommentId.toString() ===
							comment.ctCommentId.toString()
							? ' publications-fade-in'
							: ''
					}`}
					key={keyParam + '_' + comment.ctCommentId}
					ref={commentRef}
				>
					{deleting === comment.ctCommentId && (
						<div className="comment-deleting-overlay">
							<div className="comment-deleting-title">
								{Liferay.Language.get(
									'are-you-sure-you-want-to-delete-this-comment'
								)}
							</div>
							<div>
								<div className="btn-group">
									<div className="btn-group-item">
										<button
											className="btn btn-primary btn-sm"
											onClick={() =>
												handleDelete(
													comment.ctCommentId
												)
											}
											type="button"
										>
											{Liferay.Language.get('delete')}
										</button>
									</div>
									<div className="btn-group-item">
										<button
											className="btn btn-secondary btn-sm"
											onClick={() => setDeleting(0)}
											type="button"
										>
											{Liferay.Language.get('cancel')}
										</button>
									</div>
								</div>
							</div>
						</div>
					)}
					<div className="comment-row">
						<div className="autofit-padded-no-gutters-x autofit-row">
							<div className="autofit-col">
								{renderUserPortrait(comment.userId)}
							</div>
							<div className="autofit-col autofit-col-expand">
								<div className="autofit-row">
									<div className="autofit-col autofit-col-expand">
										<div className="comment-title">
											{title}
										</div>
										<div
											className="text-secondary"
											data-tooltip-align="top"
											title={comment.createDate}
										>
											{comment.timeDescription}
										</div>
									</div>
									{editing !== comment.ctCommentId && (
										<div className="autofit-col">
											<ClayDropDownWithItems
												alignmentPosition={
													Align.BottomLeft
												}
												items={dropdownItems}
												spritemap={spritemap}
												trigger={
													<ClayButtonWithIcon
														disabled={
															deleting ===
															comment.ctCommentId
														}
														displayType="unstyled"
														small
														spritemap={spritemap}
														symbol="ellipsis-v"
													/>
												}
											/>
										</div>
									)}
								</div>
							</div>
						</div>
						<div className="autofit-row">{commentBody}</div>
					</div>
				</div>
			);
		}

		return <>{items}</>;
	};

	let count = 0;

	if (fetchData && fetchData.comments) {
		count = fetchData.comments.length;
	}

	return (
		<div className="publications-comments">
			<nav className="component-tbar tbar">
				<div className="container-fluid">
					<ul className="tbar-nav">
						<li className="tbar-item tbar-item-expand">
							{Liferay.Util.sub(
								count === 1
									? Liferay.Language.get('x-comment')
									: Liferay.Language.get('x-comments'),
								count.toString()
							)}
						</li>
						<li className="tbar-item">
							<ClayButtonWithIcon
								displayType="unstyled"
								onClick={() => {
									setShowComments(false);
								}}
								spritemap={spritemap}
								symbol="times"
							/>
						</li>
					</ul>
				</div>
			</nav>
			<div
				className={`sidebar-body${
					fetchData && loading ? ' publications-loading' : ''
				}`}
				ref={sidebarRef}
			>
				{fetchData && fetchData.errorMessage && (
					<div className="autofit-padded-no-gutters-x autofit-row">
						<ClayAlert
							displayType="danger"
							spritemap={spritemap}
							title={Liferay.Language.get('error')}
						>
							{fetchData.errorMessage}
						</ClayAlert>
					</div>
				)}
				{renderComments()}
			</div>
			<div className="sidebar-footer">
				<ClayForm.Group>
					<ClayInput
						aria-label={Liferay.Language.get('comment')}
						component="textarea"
						onChange={(event) => setInputValue(event.target.value)}
						placeholder={Liferay.Language.get(
							'type-your-comment-here'
						)}
						type="text"
						value={inputValue}
					/>
				</ClayForm.Group>
				<div>
					<button
						className={`btn btn-primary${
							loading || inputValue ? '' : ' disabled'
						}`}
						onClick={() => handleReply()}
						type="button"
					>
						{Liferay.Language.get('reply')}
					</button>
				</div>
			</div>
		</div>
	);
};
