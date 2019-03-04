import EditableImageFragmentProcessor from './EditableImageFragmentProcessor.es';
import EditableLinkFragmentProcessor from './EditableLinkFragmentProcessor.es';
import EditableTextFragmentProcessor from './EditableTextFragmentProcessor.es';

const FragmentProcessors = {
	fallback: {
		destroy: EditableTextFragmentProcessor.destroy,
		getFloatingToolbarPanels: EditableTextFragmentProcessor.getFloatingToolbarPanels,
		init: EditableTextFragmentProcessor.init,
		render: EditableTextFragmentProcessor.render
	},

	image: {
		destroy: EditableImageFragmentProcessor.destroy,
		getFloatingToolbarPanels: EditableImageFragmentProcessor.getFloatingToolbarPanels,
		init: EditableImageFragmentProcessor.init,
		render: EditableImageFragmentProcessor.render
	},

	link: {
		destroy: EditableLinkFragmentProcessor.destroy,
		getFloatingToolbarPanels: EditableLinkFragmentProcessor.getFloatingToolbarPanels,
		init: EditableLinkFragmentProcessor.init,
		render: EditableLinkFragmentProcessor.render
	}
};

export {FragmentProcessors};
export default FragmentProcessors;