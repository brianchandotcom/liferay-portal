package com.liferay.portal.util;

import org.junit.Assert;
import org.junit.Test;

public class HtmlImplTest {

	@Test
	public void testEscapeTrivialValues() {
		_assertUnchangedEscape("");
		_assertUnchangedEscape(" ");
		Assert.assertNull(_htmlImpl.escape(null));
		_assertUnchangedEscape("text");
		_assertUnchangedEscape("CAPITAL lowercase Text");
		_assertUnchangedEscape("  no trimming performed ");
		_assertUnchangedEscape(";");
	}

	@Test
	public void testEscapeHtmlEncoding() {
		Assert.assertEquals("&lt;", _htmlImpl.escape("<"));
		Assert.assertEquals("&gt;", _htmlImpl.escape(">"));
		Assert.assertEquals("&lt;script&gt;", _htmlImpl.escape("<script>"));
		Assert.assertEquals("&amp;", _htmlImpl.escape("&"));
		Assert.assertEquals("You &amp; Me", _htmlImpl.escape("You & Me"));
		Assert.assertEquals("&lt;span class=&#034;test&#034;&gt;Test&lt;/span&gt;",
				_htmlImpl.escape("<span class=\"test\">Test</span>"));
		Assert.assertEquals(
			"I&#039;m quoting: &#034;this is a quote&#034;",
			_htmlImpl.escape("I'm quoting: \"this is a quote\""));
	}

	@Test
	public void testExtraction() {
		Assert.assertEquals(
			"whitespace removal",
			_htmlImpl.extractText("   whitespace \n   <br/> removal   "));
		Assert.assertEquals(
				"script removal",
				_htmlImpl.extractText("script <script>   test   </script> removal"));
		Assert.assertEquals(
			"HTML attribute removal",
			_htmlImpl.extractText(
				"<h1>HTML</h1> <i>attribute</i> <strong>removal</strong>"));
		Assert.assertEquals(
				"onclick removal",
				_htmlImpl.extractText("<div onclick=\"honk()\">onclick removal</div>"));
	}

	@Test
	public void testNewLineConversion() {
		Assert.assertEquals(
			"one<br />two<br />three<br /><br />five",
			_htmlImpl.replaceNewLine("one\ntwo\r\nthree\n\nfive"));
	}

	@Test
	public void testStripBetween() {
		Assert.assertNull(_htmlImpl.stripBetween(null, "test"));
		Assert.assertEquals("test--test", _htmlImpl.stripBetween(
				"test-<honk>thiswillbestripped</honk>-test", "honk"));
		Assert.assertEquals(
			"test--test",
			_htmlImpl.stripBetween(
				"test-<honk attribute=\"value\">thiswillbestripped</honk>-test",
				"honk"));
		Assert.assertEquals("test-test-test",
				_htmlImpl.stripBetween("test-test-test", "test"));
		Assert.assertEquals(
			"works across  lines", _htmlImpl.stripBetween(
			"works across <honk>\r\n a number of </honk> lines", "honk"));
		Assert.assertEquals("test-test-test",
				_htmlImpl.stripBetween("test-test-test", "test"));
		Assert.assertEquals(
			"multiple occurrences, multiple indeed",
			_htmlImpl.stripBetween(
				"multiple <a>many</a>occurrences, multiple <a>HONK</a>indeed",
				"a"));
		Assert.assertEquals("self-closing <test/> is unhandled",
				_htmlImpl.stripBetween("self-closing <test/> is unhandled",
						"test"));
		Assert.assertEquals(
			"self-closing <test /> is unhandled",
			_htmlImpl.stripBetween(
				"self-closing <test /> is unhandled",
				"test"));
	}

	@Test
	public void testStripComments() {
		Assert.assertNull(_htmlImpl.stripComments(null));
		Assert.assertEquals("", _htmlImpl.stripComments("<!-- bla -->"));
		Assert.assertEquals("", _htmlImpl.stripComments("<!---->"));
		Assert.assertEquals("test",
				_htmlImpl.stripComments("te<!--  bla -->s<!-- bla bla -->t"));
		Assert.assertEquals("test", _htmlImpl.stripComments("te<!-- \n -->st"));
		Assert.assertEquals("test", _htmlImpl.stripComments("<!--  bla -->test"));
		Assert.assertEquals("test", _htmlImpl.stripComments("test<!--  bla -->"));
		Assert.assertEquals("test", _htmlImpl.stripComments("te<!-- --><!-- -->st"));
	}

	private HtmlImpl _htmlImpl = new HtmlImpl();

	private void _assertUnchangedEscape(String input) {
		Assert.assertEquals(input, _htmlImpl.escape(input));
	}

}