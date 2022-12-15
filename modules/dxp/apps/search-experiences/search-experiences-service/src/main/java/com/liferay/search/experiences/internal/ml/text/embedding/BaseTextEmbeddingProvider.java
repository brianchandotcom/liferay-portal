/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.ml.text.embedding;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.BreakIterator;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseTextEmbeddingProvider {

	protected String extractSentences(
		int maxCharacterCount, String text, String truncationStrategy) {

		text = StringUtil.trim(HtmlUtil.stripHtml(text));

		if (maxCharacterCount <= 0) {
			maxCharacterCount = 50;
		}

		if (text.length() <= maxCharacterCount) {
			return text;
		}

		if (truncationStrategy.equals("end")) {
			return _extractSentencesFromEnd(maxCharacterCount, text);
		}
		else if (truncationStrategy.equals("middle")) {
			return _extractSentencesFromMiddle(maxCharacterCount, text);
		}
		else {
			return _extractSentencesFromBeginning(maxCharacterCount, text);
		}
	}

	private String _extractSentencesFromBeginning(
		int maxCharacters, String text) {

		BreakIterator breakIterator = BreakIterator.getSentenceInstance();

		breakIterator.setText(text);

		return text.substring(0, breakIterator.preceding(maxCharacters));
	}

	private String _extractSentencesFromEnd(int maxCharacters, String text) {
		BreakIterator breakIterator = BreakIterator.getSentenceInstance();

		breakIterator.setText(text);

		return text.substring(
			breakIterator.following(text.length() - maxCharacters));
	}

	private String _extractSentencesFromMiddle(int maxCharacters, String text) {
		BreakIterator breakIterator = BreakIterator.getSentenceInstance();

		breakIterator.setText(text);

		int offset = text.length() - maxCharacters;

		int startOffset = (int)Math.ceil(offset / 2);

		int start = breakIterator.following(startOffset);

		int endOffset = text.length() - (int)Math.floor(offset / 2);

		int end = breakIterator.preceding(endOffset);

		if (((end - start) < 0) && (end != BreakIterator.DONE)) {
			end = breakIterator.following(endOffset);
		}

		if ((end - start) < 0) {
			start = breakIterator.preceding(startOffset);
		}

		if ((end - start) < 0) {
			return _extractSentencesFromBeginning(maxCharacters, text);
		}

		return text.substring(start, end);
	}

}