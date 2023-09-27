/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
