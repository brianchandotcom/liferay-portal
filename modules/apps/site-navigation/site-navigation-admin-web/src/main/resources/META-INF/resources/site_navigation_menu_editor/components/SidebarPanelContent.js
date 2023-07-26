/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useSessionState} from 'frontend-js-components-web';
import {
	fetch,
	getPortletNamespace,
	objectToFormData,
	openConfirmModal,
	runScriptsInElement,
} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';
import {flushSync} from 'react-dom';

import {APP_LAYOUT_CONTENT_CLASS_NAME} from '../constants/appLayoutClassName';
import {useConstants} from '../contexts/ConstantsContext';
import {
	useSelectedMenuItemId,
	useSetSelectedMenuItemId,
} from '../contexts/SelectedMenuItemIdContext';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';

export function SidebarPanelContent({
	configButtonRef,
	contentRequestBody,
	contentUrl,
	title,
	titleId,
}) {
	const [body, setBody] = useState(null);

	const changedRef = useRef(false);

	const isMounted = useIsMounted();
	const selectedMenuItemId = useSelectedMenuItemId();
	const setSelectedMenuItemId = useSetSelectedMenuItemId();
	const setSidebarPanelId = useSetSidebarPanelId();
	const sidebarBodyRef = useRef(null);

	const {portletId, redirect} = useConstants();

	const namespace = getPortletNamespace(portletId);

	const [scrollPosition, setScrollPosition] = useSessionState(
		`${namespace}_scrollPosition`
	);

	useEffect(() => {
		if (changedRef.current) {
			confirmUnsavedChanges({
				onConfirm: () => {
					return;
				},
			});
		}

		setBody(null);

		const updateScrollPosition = () => {
			const scrollContainer = document.querySelector(
				`.${APP_LAYOUT_CONTENT_CLASS_NAME}`
			);

			if (!scrollContainer) {
				return;
			}

			setScrollPosition(scrollContainer.scrollTop);
		};

		const handleFormSubmit = () => {
			updateScrollPosition();
			setSelectedMenuItemId(selectedMenuItemId, {persist: true});
		};

		let formElements = [];

		fetch(contentUrl, {
			body: objectToFormData(
				Liferay.Util.ns(namespace, {redirect, ...contentRequestBody})
			),
			method: 'POST',
		})
			.then((response) => response.text())
			.then((responseContent) => {
				if (isMounted()) {
					flushSync(() => {
						setBody(responseContent);
					});

					changedRef.current = false;

					formElements =
						sidebarBodyRef.current?.querySelectorAll('form') || [];

					formElements.forEach((element) =>
						element.addEventListener('submit', handleFormSubmit)
					);
				}
			});

		return () => {
			formElements.forEach((formElement) => {
				formElement.removeEventListener('submit', handleFormSubmit);
			});
		};
	}, [
		contentRequestBody,
		contentUrl,
		isMounted,
		namespace,
		redirect,
		selectedMenuItemId,
		setSelectedMenuItemId,
		setScrollPosition,
		title,
	]);

	const scrollPositionRef = useRef(scrollPosition);
	scrollPositionRef.current = scrollPosition;

	useEffect(() => {
		const scrollContainer = document.querySelector(
			`.${APP_LAYOUT_CONTENT_CLASS_NAME}`
		);

		if (!scrollContainer || !scrollPositionRef.current) {
			return;
		}

		scrollContainer.scrollTop = scrollPositionRef.current;
		setScrollPosition(null);
	}, [setScrollPosition]);

	const onClose = () => {
		if (changedRef.current) {
			confirmUnsavedChanges();
		}

		setSidebarPanelId(null);
	};

	return (
		<>
			<div className="sidebar-header">
				<ClayLayout.ContentRow className="sidebar-section">
					<ClayLayout.ContentCol expand>
						<p className="component-title" id={titleId}>
							<span className="text-truncate-inline">
								<span className="text-truncate">{title}</span>

								<span className="sr-only">
									{Liferay.Language.get(
										'configuration-panel'
									)}
								</span>
							</span>
						</p>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol>
						<ClayButton
							aria-label={Liferay.Language.get(
								'close-configuration-panel'
							)}
							displayType="unstyled"
							monospaced
							onClick={onClose}
							onKeyDown={(event) => {
								if (event.key === 'Enter') {
									onClose();

									requestAnimationFrame(() => {
										if (selectedMenuItemId) {
											document
												.querySelector(
													`[data-item-id="${selectedMenuItemId}"]`
												)
												.focus();
										}
										else {
											configButtonRef.current.focus();
										}
									});
								}
							}}
							title={Liferay.Language.get(
								'close-configuration-panel'
							)}
						>
							<ClayIcon symbol="times" />
						</ClayButton>
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</div>

			<div className="sidebar-body" ref={sidebarBodyRef}>
				{!body ? (
					<ClayLoadingIndicator />
				) : (
					<SidebarBody
						body={body}
						onChange={() => {
							changedRef.current = true;
						}}
					/>
				)}
			</div>
		</>
	);
}

SidebarPanelContent.propTypes = {
	contentRequestBody: PropTypes.object,
	contentUrl: PropTypes.string,
	title: PropTypes.string,
};

class SidebarBody extends React.Component {
	constructor(props) {
		super(props);

		this._handleOnChange = this._handleOnChange.bind(this);
		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			runScriptsInElement(this._ref.current);

			this._ref.current.addEventListener('change', this._handleOnChange);
		}
	}

	componentWillUnmount() {
		if (this._ref.current) {
			this._ref.current.removeEventListener(
				'change',
				this._handleOnChange
			);
		}
	}

	shouldComponentUpdate() {
		return false;
	}

	_handleOnChange() {
		if (this.props.onChange) {
			this.props.onChange();
		}
	}

	render() {
		return (
			<div
				dangerouslySetInnerHTML={{__html: this.props.body}}
				ref={this._ref}
			/>
		);
	}
}

function confirmUnsavedChanges({onConfirm}) {
	const form = document.querySelector(`.sidebar-body form`);

	const error = form ? form.querySelector('.has-error') : null;

	if (!error) {
		openConfirmModal({
			message: Liferay.Language.get(
				'you-have-unsaved-changes.-do-you-want-to-save-them'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					if (onConfirm) {
						onConfirm();
					}

					if (form) {
						form.submit();
					}
				}
			},
		});
	}
}
