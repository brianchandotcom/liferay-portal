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
import {Context as ModalContext} from '@clayui/modal';
import {Sidebar} from 'dynamic-data-mapping-form-builder';
import {useConfig, useForm} from 'dynamic-data-mapping-form-renderer';
import React, {useContext, useEffect, useImperativeHandle, useRef} from 'react';

class NoRender extends React.Component {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		const {forwardRef, ...otherProps} = this.props;

		return <div ref={forwardRef} {...otherProps} />;
	}
}

const createRevertFieldChanges = (onClose) => ({onClick, type}) => ({
	payload: {
		body: Liferay.Language.get('are-you-sure-you-want-to-cancel'),
		footer: [
			null,
			null,
			<ClayButton.Group key={3} spaced>
				<ClayButton displayType="secondary" onClick={onClose}>
					{Liferay.Language.get('dismiss')}
				</ClayButton>
				<ClayButton onClick={(event) => onClick(event, onClose)}>
					{Liferay.Language.get('yes-cancel')}
				</ClayButton>
			</ClayButton.Group>,
		],
		header: Liferay.Language.get('cancel-field-changes-question'),
		size: 'sm',
	},
	type,
});

export const MetalSidebarAdapter = React.forwardRef((props, ref) => {
	const {
		defaultLanguageId,
		fieldSetDefinitionURL,
		fieldTypes,
		portletNamespace,
		spritemap,
		view,
	} = useConfig();
	const dispatch = useForm();
	const [{onClose}, modalDispatch] = useContext(ModalContext);

	const component = useRef(null);
	const container = useRef(null);

	useEffect(() => {
		if (!component.current && container.current) {
			component.current = new Sidebar(
				{
					...props,
					defaultLanguageId,
					dispatch: (type, event) => dispatch({payload: event, type}),
					fieldSetDefinitionURL,
					fieldTypes,
					modalDispatch,
					portletNamespace,
					revertFieldChanges: createRevertFieldChanges(onClose),
					spritemap,
					view,
				},
				container.current
			);
		}

		return () => {
			if (component.current) {
				component.current.dispose();
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (component.current) {
			component.current.props = {
				...component.current.props,
				...props,
			};
			component.current.forceUpdate();
		}
	}, [component, props]);

	useImperativeHandle(ref, () => component, []);

	return <NoRender forwardRef={container} />;
});
