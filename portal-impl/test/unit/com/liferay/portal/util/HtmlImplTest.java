package com.liferay.portal.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class HtmlImplTest {
	private HtmlImpl htmlImpl = new HtmlImpl();

	private void assertUnchangedEscape(String input) {
		assertEquals(input, htmlImpl.escape(input));
	}

	@Test
	public void testEscapeTrivialValues() {
		assertUnchangedEscape("");
		assertUnchangedEscape(" ");
		assertNull(htmlImpl.escape(null));
		assertUnchangedEscape("text");
		assertUnchangedEscape("CAPITAL lowercase Text");
		assertUnchangedEscape("  no trimming performed ");
		assertUnchangedEscape(";");
	}

	@Test
	public void testEscapeHtmlEncoding() {
		assertEquals("&lt;", htmlImpl.escape("<"));
		assertEquals("&gt;", htmlImpl.escape(">"));
		assertEquals("&lt;script&gt;", htmlImpl.escape("<script>"));
		assertEquals("&amp;", htmlImpl.escape("&"));
		assertEquals("You &amp; Me", htmlImpl.escape("You & Me"));
		assertEquals("&lt;span class=&#034;test&#034;&gt;Test&lt;/span&gt;",
				htmlImpl.escape("<span class=\"test\">Test</span>"));
		assertEquals("I&#039;m quoting: &#034;this is a quote&#034;",
				htmlImpl.escape("I'm quoting: \"this is a quote\""));
	}

	@Test
	public void testExtraction() {
		assertEquals("whitespace removal",
				htmlImpl.extractText("   whitespace \n   <br/> removal   "));
		assertEquals(
				"script removal",
				htmlImpl.extractText("script <script>   test   </script> removal"));
		assertEquals(
				"HTML attribute removal",
				htmlImpl.extractText("<h1>HTML</h1> <i>attribute</i> <strong>removal</strong>"));
		assertEquals(
				"onclick removal",
				htmlImpl.extractText("<div onclick=\"honk()\">onclick removal</div>"));
	}

	@Test
	public void testNewLineConversion() {
		assertEquals("one<br />two<br />three<br /><br />five",
				htmlImpl.replaceNewLine("one\ntwo\r\nthree\n\nfive"));
	}

	@Test
	public void testStripBetween() {
		assertNull(htmlImpl.stripBetween(null, "test"));
		assertEquals("test--test", htmlImpl.stripBetween(
				"test-<honk>thiswillbestripped</honk>-test", "honk"));
		assertEquals(
				"test--test",
				htmlImpl.stripBetween(
						"test-<honk attribute=\"value\">thiswillbestripped</honk>-test",
						"honk"));
		assertEquals("test-test-test",
				htmlImpl.stripBetween("test-test-test", "test"));
		assertEquals("works across  lines", htmlImpl.stripBetween(
				"works across <honk>\r\n a number of </honk> lines", "honk"));
		assertEquals("test-test-test",
				htmlImpl.stripBetween("test-test-test", "test"));
		assertEquals(
				"multiple occurrences, multiple indeed",
				htmlImpl.stripBetween(
						"multiple <a>many</a>occurrences, multiple <a>HONK</a>indeed",
						"a"));
		assertEquals("self-closing <test/> is unhandled",
				htmlImpl.stripBetween("self-closing <test/> is unhandled",
						"test"));
		assertEquals("self-closing <test /> is unhandled",
				htmlImpl.stripBetween("self-closing <test /> is unhandled",
						"test"));
	}

	@Test
	public void testStripComments() {
		assertNull(htmlImpl.stripComments(null));
		assertEquals("", htmlImpl.stripComments("<!-- bla -->"));
		assertEquals("", htmlImpl.stripComments("<!---->"));
		assertEquals("test",
				htmlImpl.stripComments("te<!--  bla -->s<!-- bla bla -->t"));
		assertEquals("test", htmlImpl.stripComments("te<!-- \n -->st"));
		assertEquals("test", htmlImpl.stripComments("<!--  bla -->test"));
		assertEquals("test", htmlImpl.stripComments("test<!--  bla -->"));
		assertEquals("test", htmlImpl.stripComments("te<!-- --><!-- -->st"));
	}

}
