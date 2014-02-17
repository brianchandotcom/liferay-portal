package com.liferay.portal.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class HtmlImplTest {

	@Test
	public void testEscapeTrivialValues() {
		_assertUnchangedEscape("");
		_assertUnchangedEscape(" ");
		assertNull(_htmlImpl.escape(null));
		_assertUnchangedEscape("text");
		_assertUnchangedEscape("CAPITAL lowercase Text");
		_assertUnchangedEscape("  no trimming performed ");
		_assertUnchangedEscape(";");
	}

	@Test
	public void testEscapeHtmlEncoding() {
		assertEquals("&lt;", _htmlImpl.escape("<"));
		assertEquals("&gt;", _htmlImpl.escape(">"));
		assertEquals("&lt;script&gt;", _htmlImpl.escape("<script>"));
		assertEquals("&amp;", _htmlImpl.escape("&"));
		assertEquals("You &amp; Me", _htmlImpl.escape("You & Me"));
		assertEquals("&lt;span class=&#034;test&#034;&gt;Test&lt;/span&gt;",
				_htmlImpl.escape("<span class=\"test\">Test</span>"));
		assertEquals(
			"I&#039;m quoting: &#034;this is a quote&#034;",
			_htmlImpl.escape("I'm quoting: \"this is a quote\""));
	}

	@Test
	public void testExtraction() {
		assertEquals(
			"whitespace removal",
			_htmlImpl.extractText("   whitespace \n   <br/> removal   "));
		assertEquals(
				"script removal",
				_htmlImpl.extractText("script <script>   test   </script> removal"));
		assertEquals(
			"HTML attribute removal",
			_htmlImpl.extractText(
				"<h1>HTML</h1> <i>attribute</i> <strong>removal</strong>"));
		assertEquals(
				"onclick removal",
				_htmlImpl.extractText("<div onclick=\"honk()\">onclick removal</div>"));
	}

	@Test
	public void testNewLineConversion() {
		assertEquals(
			"one<br />two<br />three<br /><br />five",
			_htmlImpl.replaceNewLine("one\ntwo\r\nthree\n\nfive"));
	}

	@Test
	public void testStripBetween() {
		assertNull(_htmlImpl.stripBetween(null, "test"));
		assertEquals("test--test", _htmlImpl.stripBetween(
				"test-<honk>thiswillbestripped</honk>-test", "honk"));
		assertEquals(
			"test--test",
			_htmlImpl.stripBetween(
				"test-<honk attribute=\"value\">thiswillbestripped</honk>-test",
				"honk"));
		assertEquals("test-test-test",
				_htmlImpl.stripBetween("test-test-test", "test"));
		assertEquals(
			"works across  lines", _htmlImpl.stripBetween(
			"works across <honk>\r\n a number of </honk> lines", "honk"));
		assertEquals("test-test-test",
				_htmlImpl.stripBetween("test-test-test", "test"));
		assertEquals(
			"multiple occurrences, multiple indeed",
			_htmlImpl.stripBetween(
				"multiple <a>many</a>occurrences, multiple <a>HONK</a>indeed",
				"a"));
		assertEquals("self-closing <test/> is unhandled",
				_htmlImpl.stripBetween("self-closing <test/> is unhandled",
						"test"));
		assertEquals(
			"self-closing <test /> is unhandled",
			_htmlImpl.stripBetween(
				"self-closing <test /> is unhandled",
				"test"));
	}

	@Test
	public void testStripComments() {
		assertNull(_htmlImpl.stripComments(null));
		assertEquals("", _htmlImpl.stripComments("<!-- bla -->"));
		assertEquals("", _htmlImpl.stripComments("<!---->"));
		assertEquals("test",
				_htmlImpl.stripComments("te<!--  bla -->s<!-- bla bla -->t"));
		assertEquals("test", _htmlImpl.stripComments("te<!-- \n -->st"));
		assertEquals("test", _htmlImpl.stripComments("<!--  bla -->test"));
		assertEquals("test", _htmlImpl.stripComments("test<!--  bla -->"));
		assertEquals("test", _htmlImpl.stripComments("te<!-- --><!-- -->st"));
	}

	private HtmlImpl _htmlImpl = new HtmlImpl();

	private void _assertUnchangedEscape(String input) {
		assertEquals(input, _htmlImpl.escape(input));
	}

}
