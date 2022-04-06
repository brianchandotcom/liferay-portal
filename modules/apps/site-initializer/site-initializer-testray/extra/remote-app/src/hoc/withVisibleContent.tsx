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

import {Component} from 'react';

import {FormModalComponent} from '../hooks/useFormModal';

export function withVisibleContent<T extends object>(
	WrappedComponent: React.ComponentType<T>
) {
	return class extends Component<T & FormModalComponent> {
		render() {
			if (this.props.modal.visible) {
				return <WrappedComponent {...this.props} />;
			}

			return null;
		}
	};
}
