package com.liferay.knowledge.base.markdown.converter.internal.flexmark.util;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;

public class FlexmarkUtil {
	
	public static String BStoString(BasedSequence sequence) {
		String string = "";
		for (int i=0; i < sequence.length(); i++) {
			char c = sequence.charAt(i);
			string = string + c;
		}
		
		return string;
	}
	
	public static BasedSequence StringtoBS (String string) {
		CharSequence seq = string;
		BasedSequence bs = CharSubSequence.of(string.toCharArray(), 0, string.length());
		return bs;
		
		
	}

}
