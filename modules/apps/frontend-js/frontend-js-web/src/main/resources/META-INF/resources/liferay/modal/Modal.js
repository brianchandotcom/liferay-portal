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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {render} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import './Modal.scss';
import delegate from '../delegate/delegate.es';
import {escapeHTML} from '../util/html_util';
import navigate from '../util/navigate.es';

const openAlertModal = ({message}) => {
	openModal({
		bodyHTML: escapeHTML(message),
		buttons: [
			{
				label: Liferay.Language.get('ok'),
				onClick: ({processClose}) => {
					processClose();
				},
				otherProps: {
					autoFocus: true,
				},
			},
		],
		size: 'sm',
		title: Liferay.Language.get('alert'),
		withTitle: false,
	});
};

const Modal = ({
	bodyHTML,
	buttons,
	containerProps = {
		className: 'cadmin',
	},
	customEvents,
	disableAutoClose,
	footerCssClass,
	headerCssClass,
	headerHTML,
	height,
	id,
	iframeBodyCssClass,
	iframeProps = {},
	onClose,
	onOpen,
	size,
	status,
	title,
	url,
	withTitle,
	zIndex,
}) => {
	const [loading, setLoading] = useState(true);
	const [visible, setVisible] = useState(true);

	const eventHandlersRef = useRef([]);

	const processClose = useCallback(() => {
		setVisible(false);

		document.body.classList.remove('modal-open');

		const eventHandlers = eventHandlersRef.current;

		eventHandlers.forEach((eventHandler) => {
			eventHandler.detach();
		});

		eventHandlers.splice(0, eventHandlers.length);

		if (onClose) {
			onClose();
		}
	}, [eventHandlersRef, onClose]);

	const {observer} = useModal({
		onClose: () => processClose(),
	});

	const onButtonClick = ({formId, onClick, type}) => {
		if (type === 'cancel') {
			processClose();
		}
		else if (url && type === 'submit') {
			const iframe = document.querySelector('.liferay-modal iframe');

			if (iframe) {
				const iframeDocument = iframe.contentWindow.document;

				const forms = iframeDocument.querySelectorAll('form');

				if (
					forms.length !== 1 &&
					process.env.NODE_ENV === 'development'
				) {
					console.warn('There should be one form within a modal.');
				}

				if (formId) {
					const form = iframeDocument.getElementById(formId);

					if (form) {
						form.submit();
					}
				}
				else if (forms.length >= 1) {
					forms[0].submit();
				}
			}
		}

		if (onClick) {
			onClick({processClose});
		}
	};

	const Body = ({html}) => {
		const bodyRef = useRef();

		useEffect(() => {
			const fragment = document
				.createRange()
				.createContextualFragment(html);

			bodyRef.current.innerHTML = '';

			bodyRef.current.appendChild(fragment);

			if (onOpen) {
				onOpen({container: fragment, processClose});
			}
		}, [html]);

		return <div className="liferay-modal-body" ref={bodyRef}></div>;
	};

	useEffect(() => {
		const eventHandlers = eventHandlersRef.current;

		if (customEvents) {
			customEvents.forEach((customEvent) => {
				if (customEvent.name && customEvent.onEvent) {
					const eventHandler = Liferay.on(
						customEvent.name,
						(event) => {
							customEvent.onEvent(event);
						}
					);

					eventHandlers.push(eventHandler);
				}
			});
		}

		const closeEventHandler = Liferay.on('closeModal', (event) => {
			if (event.id && id && event.id !== id) {
				return;
			}

			processClose();

			if (event.redirect) {
				navigate(event.redirect);
			}
		});

		eventHandlers.push(closeEventHandler);

		return () => {
			eventHandlers.forEach((eventHandler) => {
				eventHandler.detach();
			});

			eventHandlers.splice(0, eventHandlers.length);
		};
	}, [customEvents, eventHandlersRef, id, onClose, onOpen, processClose]);

	return (
		<>
			{visible && (
				<ClayModal
					className="liferay-modal"
					containerProps={{...containerProps}}
					disableAutoClose={disableAutoClose}
					id={id}
					observer={observer}
					size={url && !size ? 'full-screen' : size}
					status={status}
					zIndex={zIndex}
				>
					<ClayModal.Header
						className={headerCssClass}
						withTitle={withTitle}
					>
						{headerHTML ? (
							<div
								dangerouslySetInnerHTML={{
									__html: headerHTML,
								}}
							></div>
						) : (
							<ClayModal.Title>{title}</ClayModal.Title>
						)}
					</ClayModal.Header>

					<div
						className={classNames('modal-body', {
							'modal-body-iframe': url,
						})}
						style={{
							height,
						}}
					>
						{url ? (
							<>
								{loading && <ClayLoadingIndicator />}
								<Iframe
									iframeBodyCssClass={iframeBodyCssClass}
									iframeProps={{
										id: id && `${id}_iframe_`,
										...iframeProps,
									}}
									onOpen={onOpen}
									processClose={processClose}
									title={title}
									updateLoading={(loading) => {
										setLoading(loading);
									}}
									url={url}
								/>
							</>
						) : (
							<>{bodyHTML && <Body html={bodyHTML} />}</>
						)}
					</div>

					{buttons && (
						<ClayModal.Footer
							className={footerCssClass}
							last={
								<ClayButton.Group spaced>
									{buttons.map((button, index) => (
										<ClayButton
											displayType={button.displayType}
											id={button.id}
											key={index}
											onClick={() => {
												onButtonClick(button);
											}}
											type={
												button.type === 'cancel'
													? 'button'
													: button.type
											}
										>
											{button.label}
										</ClayButton>
									))}
								</ClayButton.Group>
							}
						/>
					)}
				</ClayModal>
			)}
		</>
	);
};

const openModal = (props) => {
	if (
		props &&
		props.url &&
		props.bodyHTML &&
		process.env.NODE_ENV === 'development'
	) {
		console.warn(
			'url and bodyHTML props are both set. bodyHTML will be ignored. Please use one or another.'
		);
	}

	// Mount in detached node; Clay will take care of appending to `document.body`.
	// See: https://github.com/liferay/clay/blob/master/packages/clay-shared/src/Portal.tsx

	render(Modal, props, document.createElement('div'));
};

const openPortletModal = ({
	containerProps,
	footerCssClass,
	headerCssClass,
	iframeBodyCssClass,
	onClose,
	portletSelector,
	subTitle,
	title,
	url,
}) => {
	const portlet = document.querySelector(portletSelector);

	if (portlet && url) {
		const titleElement =
			portlet.querySelector('.portlet-title') ||
			portlet.querySelector('.portlet-title-default');

		if (titleElement) {
			if (portlet.querySelector('#cpPortletTitle')) {
				const titleTextElement = titleElement.querySelector(
					'.portlet-title-text'
				);

				if (titleTextElement) {
					title = `${titleTextElement.outerHTML} - ${title}`;
				}
			}
			else {
				title = `${titleElement.textContent} - ${title}`;
			}
		}

		let headerHTML;

		if (subTitle) {
			headerHTML = `${title}<div class="portlet-configuration-subtitle small"><span class="portlet-configuration-subtitle-text">${subTitle}</span></div>`;
		}

		openModal({
			containerProps,
			footerCssClass,
			headerCssClass,
			headerHTML,
			iframeBodyCssClass,
			onClose,
			title,
			url,
		});
	}
};

/**
 * A utility with API that matches Liferay.Portlet.openWindow. The purpose of
 * this utility is backwards compatibility.
 * @deprecated As of Athanasius (7.3.x), replaced by Liferay.Portlet.openModal
 */
const openPortletWindow = ({bodyCssClass, portlet, uri, ...otherProps}) => {
	openPortletModal({
		iframeBodyCssClass: bodyCssClass,
		portletSelector: portlet,
		url: uri,
		...otherProps,
	});
};

const openSelectionModal = ({
	buttonAddLabel = Liferay.Language.get('add'),
	buttonCancelLabel = Liferay.Language.get('cancel'),
	containerProps,
	customSelectEvent = false,
	getSelectedItemsOnly = true,
	height,
	id,
	iframeBodyCssClass,
	multiple = false,
	onClose,
	onSelect,
	selectEventName,
	selectedData,
	selectedDataCheckboxesDisabled = false,
	size,
	title,
	url,
	zIndex,
}) => {
	const eventHandlers = [];
	let iframeWindowObj;
	let processCloseFn;
	let selectedItem;

	const select = () => {
		if (multiple && !selectedItem) {
			const searchContainer = iframeWindowObj.document.querySelector(
				'.searchcontainer'
			);

			if (searchContainer) {
				iframeWindowObj.Liferay.componentReady(searchContainer.id).then(
					(searchContainer) => {
						const allSelectedElements = getSelectedItemsOnly
							? searchContainer.select.getAllSelectedElements()
							: searchContainer.select._getAllElements(false);

						const allSelectedNodes = allSelectedElements.getDOMNodes();

						onSelect(
							allSelectedNodes.map((node) => {
								let item = {};

								if (node.value) {
									item.value = node.value;
								}

								if (!getSelectedItemsOnly && node.checked) {
									item.checked = node.checked;
								}

								const row = node.closest('dd, tr, li');

								if (row && Object.keys(row.dataset).length) {
									item = {...item, ...row.dataset};
								}

								return item;
							})
						);

						processCloseFn();
					}
				);
			}
		}
		else {
			onSelect(selectedItem);

			processCloseFn();
		}
	};

	openModal({
		buttons: multiple
			? [
					{
						displayType: 'secondary',
						label: buttonCancelLabel,
						type: 'cancel',
					},
					{
						label: buttonAddLabel,
						onClick: select,
					},
			  ]
			: null,
		containerProps,
		height,
		id: id || selectEventName,
		iframeBodyCssClass,
		onClose: () => {
			eventHandlers.forEach((eventHandler) => {
				eventHandler.detach();
			});

			eventHandlers.splice(0, eventHandlers.length);

			if (onClose) {
				onClose();
			}
		},
		onOpen: ({iframeWindow, processClose}) => {
			iframeWindowObj = iframeWindow;
			processCloseFn = processClose;

			const iframeBody = iframeWindow.document.body;

			const itemElements = iframeBody.querySelectorAll(
				'.selector-button'
			);

			if (selectedData) {
				const selectedDataSet = new Set(selectedData);

				itemElements.forEach((itemElement) => {
					const itemId =
						itemElement.dataset.entityid ||
						itemElement.dataset.entityname;

					if (selectedDataSet.has(itemId)) {
						itemElement.disabled = true;
						itemElement.classList.add('disabled');
					}
					else {
						itemElement.disabled = false;
						itemElement.classList.remove('disabled');
					}
				});

				if (multiple) {
					for (const row of iframeBody.querySelectorAll(
						'.searchcontainer tr'
					)) {
						const itemId =
							row.dataset.entityid || row.dataset.entityname;

						if (selectedDataSet.has(itemId)) {
							const checkbox = row.querySelector(
								'input[type="checkbox"]'
							);

							if (!checkbox) {
								continue;
							}

							checkbox.checked = true;

							if (selectedDataCheckboxesDisabled) {
								checkbox.disabled = true;
							}
						}
					}
				}
			}

			if (selectEventName) {
				const selectEventHandler = Liferay.on(
					selectEventName,
					(event) => {
						selectedItem = event.data || event;

						if (!multiple) {
							select();
						}
					}
				);

				eventHandlers.push(selectEventHandler);

				if (!customSelectEvent) {
					iframeBody.addEventListener('click', (event) => {
						const delegateTarget = event.target?.closest(
							'.selector-button'
						);

						if (delegateTarget) {
							Liferay.fire(
								selectEventName,
								delegateTarget.dataset
							);
						}
					});
				}
			}
		},
		size,
		title,
		url,
		zIndex,
	});
};

const CSS_CLASS_IFRAME_BODY = 'dialog-iframe-popup';

class Iframe extends React.Component {
	constructor(props) {
		super(props);

		this.delegateHandlers = [];

		this.iframeRef = React.createRef();

		const iframeURL = new URL(props.url);

		const namespace = iframeURL.searchParams.get('p_p_id');

		const bodyCssClass =
			props.iframeBodyCssClass || props.iframeBodyCssClass === ''
				? `${CSS_CLASS_IFRAME_BODY} ${props.iframeBodyCssClass}`
				: `cadmin ${CSS_CLASS_IFRAME_BODY}`;

		iframeURL.searchParams.set(`_${namespace}_bodyCssClass`, bodyCssClass);

		this.state = {src: iframeURL.toString()};
	}

	componentWillUnmount() {
		if (this.beforeScreenFlipHandler) {
			Liferay.detach(this.beforeScreenFlipHandler);
		}

		if (this.delegateHandlers.length) {
			this.delegateHandlers.forEach(({dispose}) => dispose());
			this.delegateHandlers = null;
		}
	}

	onLoadHandler = () => {
		const iframeWindow = this.iframeRef.current.contentWindow;

		this.delegateHandlers.push(
			delegate(
				iframeWindow.document,
				'click',
				'.btn-cancel,.lfr-hide-dialog',
				() => this.props.processClose()
			),
			delegate(iframeWindow.document, 'keydown', 'body', (event) => {
				if (event.key === 'Escape') {
					this.props.processClose();
				}
			})
		);

		iframeWindow.document.body.classList.add(CSS_CLASS_IFRAME_BODY);

		if (iframeWindow.Liferay.SPA) {
			this.beforeScreenFlipHandler = iframeWindow.Liferay.on(
				'beforeScreenFlip',
				() => {
					iframeWindow.document.body.classList.add(
						CSS_CLASS_IFRAME_BODY
					);
				}
			);
		}

		this.props.updateLoading(false);

		iframeWindow.onunload = () => {
			this.props.updateLoading(true);
		};

		Liferay.fire('modalIframeLoaded', {src: this.state.src});

		if (this.props.onOpen) {
			this.props.onOpen({
				iframeWindow,
				processClose: this.props.processClose,
			});
		}
	};

	render() {
		return (
			<iframe
				{...this.props.iframeProps}
				onLoad={this.onLoadHandler}
				ref={this.iframeRef}
				src={this.state.src}
				title={this.props.title}
			/>
		);
	}
}

Modal.propTypes = {
	bodyHTML: PropTypes.string,
	buttons: PropTypes.arrayOf(
		PropTypes.shape({
			displayType: PropTypes.oneOf([
				'link',
				'primary',
				'secondary',
				'unstyled',
			]),
			formId: PropTypes.string,
			id: PropTypes.string,
			label: PropTypes.string,
			onClick: PropTypes.func,
			type: PropTypes.oneOf(['cancel', 'submit']),
		})
	),
	containerProps: PropTypes.object,
	customEvents: PropTypes.arrayOf(
		PropTypes.shape({
			name: PropTypes.string,
			onEvent: PropTypes.func,
		})
	),
	headerHTML: PropTypes.string,
	height: PropTypes.string,
	id: PropTypes.string,
	iframeProps: PropTypes.object,
	onClose: PropTypes.func,
	onOpen: PropTypes.func,
	size: PropTypes.oneOf(['full-screen', 'lg', 'md', 'sm']),
	title: PropTypes.string,
	url: PropTypes.string,
};

export {
	Modal,
	openAlertModal,
	openModal,
	openPortletModal,
	openPortletWindow,
	openSelectionModal,
};
