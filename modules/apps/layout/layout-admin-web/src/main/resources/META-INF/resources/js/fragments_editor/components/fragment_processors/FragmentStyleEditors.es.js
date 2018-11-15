import BackgroundImageFragmentStyleEditor from './BackgroundImageFragmentStyleEditor.es';

const FragmentStyleEditors = {
	backgroundImage: {
		destroy: BackgroundImageFragmentStyleEditor.destroy,
		getButtons: BackgroundImageFragmentStyleEditor.getButtons,
		init: BackgroundImageFragmentStyleEditor.init
	}
};

export {FragmentStyleEditors};
export default FragmentStyleEditors;