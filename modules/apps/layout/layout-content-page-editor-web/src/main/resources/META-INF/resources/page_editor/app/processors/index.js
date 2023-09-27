/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ActionProcessor from './ActionProcessor';
import BackgroundImageProcessor from './BackgroundImageProcessor';
import DateTimeProcessor from './DateTimeProcessor';
import FallbackProcessor from './FallbackProcessor';
import HTMLProcessor from './HTMLProcessor';
import ImageProcessor from './ImageProcessor';
import LinkProcessor from './LinkProcessor';
import RichTextProcessor from './RichTextProcessor';
import TextProcessor from './TextProcessor';

export default {
	'action': ActionProcessor,
	'background-image': BackgroundImageProcessor,
	'date-time': DateTimeProcessor,
	'fallback': FallbackProcessor,
	'html': HTMLProcessor,
	'image': ImageProcessor,
	'link': LinkProcessor,
	'rich-text': RichTextProcessor,
	'text': TextProcessor,
};
