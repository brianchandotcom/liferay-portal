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

import EditableImageFragmentProcessor from './EditableImageFragmentProcessor.es';
import EditableLinkFragmentProcessor from './EditableLinkFragmentProcessor.es';
import EditableTextFragmentProcessor from './EditableTextFragmentProcessor.es';

const FragmentProcessors = {
	fallback: {
		destroy: EditableTextFragmentProcessor.destroy,
		getFloatingToolbarButtons:
			EditableTextFragmentProcessor.getFloatingToolbarButtons,
		init: EditableTextFragmentProcessor.init,
		render: EditableTextFragmentProcessor.render
	},

	image: {
		destroy: EditableImageFragmentProcessor.destroy,
		getFloatingToolbarButtons:
			EditableImageFragmentProcessor.getFloatingToolbarButtons,
		init: EditableImageFragmentProcessor.init,
		render: EditableImageFragmentProcessor.render
	},

	link: {
		destroy: EditableLinkFragmentProcessor.destroy,
		getFloatingToolbarButtons:
			EditableLinkFragmentProcessor.getFloatingToolbarButtons,
		init: EditableLinkFragmentProcessor.init,
		render: EditableLinkFragmentProcessor.render
	}
};

export {FragmentProcessors};
export default FragmentProcessors;
