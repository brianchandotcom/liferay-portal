import AccessibilityImageAlt from './buttons/accessibility/accessibility_image_alt.jsx';

import ItemSelectorAudio from './buttons/item_selector/item_selector_audio.jsx';
import ItemSelectorImage from './buttons/item_selector/item_selector_image.jsx';
import ItemSelectorVideo from './buttons/item_selector/item_selector_video.jsx';

import LinkBrowse from './buttons/link_browse/link_browse.jsx';
import LinkEditBrowse from './buttons/link_browse/link_edit_browse.jsx';

import headingTextSelectionTest from './selections/heading_selection_test.js';

AlloyEditor.Buttons[AccessibilityImageAlt.key] = AlloyEditor.AccessibilityImageAlt = AccessibilityImageAlt;

AlloyEditor.Buttons[ItemSelectorAudio.key] = AlloyEditor.ItemSelectorAudio = ItemSelectorAudio;
AlloyEditor.Buttons[ItemSelectorImage.key] = AlloyEditor.ItemSelectorImage = ItemSelectorImage;
AlloyEditor.Buttons[ItemSelectorVideo.key] = AlloyEditor.ItemSelectorVideo = ItemSelectorVideo;

AlloyEditor.Buttons[LinkBrowse.key] = AlloyEditor.LinkBrowse = LinkBrowse;
AlloyEditor.Buttons[LinkEditBrowse.key] = AlloyEditor.LinkEditBrowse = LinkEditBrowse;

AlloyEditor.SelectionTest = AlloyEditor.SelectionTest || {};

AlloyEditor.SelectionTest.headingtext = headingTextSelectionTest;