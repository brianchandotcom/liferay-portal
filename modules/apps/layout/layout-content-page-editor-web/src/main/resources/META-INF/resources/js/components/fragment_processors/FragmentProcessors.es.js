import EditableImageFragmentProcessor from './EditableImageFragmentProcessor.es';
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
	}
};

export {FragmentProcessors};
export default FragmentProcessors;