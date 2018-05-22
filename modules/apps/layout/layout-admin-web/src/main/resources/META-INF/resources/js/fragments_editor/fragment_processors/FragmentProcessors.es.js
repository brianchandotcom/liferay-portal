import EditableImageFragmentProcessor from './EditableImageFragmentProcessor.es';
import EditableTextFragmentProcessor from './EditableTextFragmentProcessor.es';

const FragmentProcessors = {
	fallback: {
		getOptions: (fragmentEditableField) => ({
			defaultEditorConfiguration: fragmentEditableField.defaultEditorConfiguration
		}),

		destroy: EditableTextFragmentProcessor.destroy,
		init: EditableTextFragmentProcessor.init
	},

	image: {
		getOptions: (fragmentEditableField) => ({
			imageSelectorURL: fragmentEditableField.imageSelectorURL
		}),

		destroy: EditableImageFragmentProcessor.destroy,
		init: EditableImageFragmentProcessor.init
	}
};

export {FragmentProcessors};
export default FragmentProcessors;