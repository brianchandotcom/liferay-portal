/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const withContextMock = (Component) => {
	return class WithContextMock extends Component {
		context = {
			dispatch: jest.fn(),
			store: {
				props: {
					editingLanguageId: this.editingLanguageId,
				},
			},
		};
	};
};

export default withContextMock;
