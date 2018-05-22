import EditableImageFragmentProcessor from './EditableImageFragmentProcessor.es';
import EditableTextFragmentProcessor from './EditableTextFragmentProcessor.es';

const FragmentProcessors = {
	fallback: {
		destroy: EditableTextFragmentProcessor.destroy,
		init: EditableTextFragmentProcessor.init
	},

	image: {
		destroy: EditableImageFragmentProcessor.destroy,
		init: EditableImageFragmentProcessor.init
	}
};

export {FragmentProcessors};
export default FragmentProcessors;