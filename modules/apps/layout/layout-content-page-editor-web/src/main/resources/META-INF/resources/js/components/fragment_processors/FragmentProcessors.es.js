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
