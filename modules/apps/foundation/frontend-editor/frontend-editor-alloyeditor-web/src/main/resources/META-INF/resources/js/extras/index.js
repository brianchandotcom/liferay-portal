import AccessibilityImageAlt from './buttons/accessibility/accessibility_image_alt.jsx';

import EmbedVideo from './buttons/embed/embed_video.jsx';
import EmbedVideoEdit from './buttons/embed/embed_video_edit.jsx';

import ItemSelectorAudio from './buttons/item_selector/item_selector_audio.jsx';
import ItemSelectorImage from './buttons/item_selector/item_selector_image.jsx';
import ItemSelectorVideo from './buttons/item_selector/item_selector_video.jsx';

import LinkBrowse from './buttons/link_browse/link_browse.jsx';
import LinkEditBrowse from './buttons/link_browse/link_edit_browse.jsx';

import './plugins/embed_video.js';

import embedVideoSelectionTest from './selections/embed_video_selection_test.js';
import headingTextSelectionTest from './selections/heading_selection_test.js';

AlloyEditor.Buttons[AccessibilityImageAlt.key] = AlloyEditor.AccessibilityImageAlt = AccessibilityImageAlt;

AlloyEditor.Buttons[EmbedVideo.key] = AlloyEditor.EmbedVideo = EmbedVideo;
AlloyEditor.Buttons[EmbedVideoEdit.key] = AlloyEditor.EmbedVideoEdit = EmbedVideoEdit;

AlloyEditor.Buttons[ItemSelectorAudio.key] = AlloyEditor.ItemSelectorAudio = ItemSelectorAudio;
AlloyEditor.Buttons[ItemSelectorImage.key] = AlloyEditor.ItemSelectorImage = ItemSelectorImage;
AlloyEditor.Buttons[ItemSelectorVideo.key] = AlloyEditor.ItemSelectorVideo = ItemSelectorVideo;

AlloyEditor.Buttons[LinkBrowse.key] = AlloyEditor.LinkBrowse = LinkBrowse;
AlloyEditor.Buttons[LinkEditBrowse.key] = AlloyEditor.LinkEditBrowse = LinkEditBrowse;

AlloyEditor.SelectionTest = AlloyEditor.SelectionTest || {};

AlloyEditor.SelectionTest.embedVideo = embedVideoSelectionTest;
AlloyEditor.SelectionTest.headingtext = headingTextSelectionTest;