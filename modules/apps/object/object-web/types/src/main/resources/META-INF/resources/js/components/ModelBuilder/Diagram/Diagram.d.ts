/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './Diagram.scss';
declare function DiagramBuilder({
	setShowModal,
}: {
	setShowModal: (value: ModelBuilderModals) => void;
}): JSX.Element;
export default DiagramBuilder;
