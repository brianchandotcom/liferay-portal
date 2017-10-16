import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './ImageEditorLoading.soy';

/**
 * ImageEditor Loading Component
 */
class ImageEditorLoading extends Component {}

// Register component
Soy.register(ImageEditorLoading, templates);

export default ImageEditorLoading;