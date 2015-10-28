/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtilTest {

    @Test
    public void testFixJSON() {
        Assert.assertEquals(
            "ABC&#09123", JenkinsResultsParserUtil.fixJSON("ABC\t123"));
        Assert.assertEquals(
            "ABC&#34123", JenkinsResultsParserUtil.fixJSON("ABC\"123"));
        Assert.assertEquals(
            "ABC&#39123", JenkinsResultsParserUtil.fixJSON("ABC'123"));
        Assert.assertEquals(
            "ABC&#40123", JenkinsResultsParserUtil.fixJSON("ABC(123"));
        Assert.assertEquals(
            "ABC&#41123", JenkinsResultsParserUtil.fixJSON("ABC)123"));
        Assert.assertEquals(
            "ABC&#60123", JenkinsResultsParserUtil.fixJSON("ABC<123"));
        Assert.assertEquals(
            "ABC&#62123", JenkinsResultsParserUtil.fixJSON("ABC>123"));
        Assert.assertEquals(
            "ABC&#91123", JenkinsResultsParserUtil.fixJSON("ABC[123"));
        Assert.assertEquals(
            "ABC&#92123", JenkinsResultsParserUtil.fixJSON("ABC\\123"));
        Assert.assertEquals(
            "ABC&#93123", JenkinsResultsParserUtil.fixJSON("ABC]123"));
        Assert.assertEquals(
            "ABC&#123123", JenkinsResultsParserUtil.fixJSON("ABC{123"));
        Assert.assertEquals(
            "ABC&#125123", JenkinsResultsParserUtil.fixJSON("ABC}123"));
        Assert.assertEquals(
            "ABC&<br />123", JenkinsResultsParserUtil.fixJSON("ABC\n123"));
    }

    @Test
    public void testFixURL() {
        Assert.assertEquals(
            "ABC%28123", JenkinsResultsParserUtil.fixURL("ABC(123"));
        Assert.assertEquals(
            "ABC%29123", JenkinsResultsParserUtil.fixURL("ABC)123"));
        Assert.assertEquals(
            "ABC%5B123", JenkinsResultsParserUtil.fixURL("ABC[123"));
        Assert.assertEquals(
            "ABC%5D123", JenkinsResultsParserUtil.fixURL("ABC]123"));
    }

    @Test
    public void testGetAxisVariable() throws Exception {
        JSONObject axisJson =
            new JSONObject(
                _read(
                    new File(_dependenciesDir, "sample-1.json")));

        Assert.assertEquals(
            "ABC", JenkinsResultsParserUtil.getAxisVariable(axisJson));
    }

    @Test
    public void testGetJobVariant() throws Exception {
        assertgetJobVariantSample(
            "sample-2.json", "functional-tomcat-mysql/0");
        assertgetJobVariantSample(
            "sample-3.json", "integration-db2");
    }

    public void assertgetJobVariantSample(
        String fileName, String expectedVariantString)
            throws Exception {

        String jsonString = _read(new File(
            _dependenciesDir, fileName));

        JSONObject jsonObject = new JSONObject(jsonString);

        Assert.assertEquals(
            expectedVariantString,
            JenkinsResultsParserUtil.getJobVariant(jsonObject));
    }

    @Test
    public void testGetLocalURL() {
        Assert.assertEquals(
            "http://test-8/8",
            JenkinsResultsParserUtil.getLocalURL("https://test-liferay.com/8"));
        Assert.assertEquals(
            "http://test-1-20/",
            JenkinsResultsParserUtil.getLocalURL(
                "https://test-1-20.liferay.com"));
        Assert.assertEquals(
            "http://test-4-1/",
            JenkinsResultsParserUtil.getLocalURL("http://test-4-1"));
    }

    @Test
    public void testToJSONObject() throws Exception {
        assertToJSONObjectSample("sample-2.json");
        assertToJSONObjectSample("sample-3.json");
    }

    protected void assertToJSONObjectSample(String fileName) throws Exception {
        File jsonFile = new File(_dependenciesDir, fileName);

        JSONObject expectedJSONObject = new JSONObject(
            _read(jsonFile));
        JSONObject actualJSONObject =
            JenkinsResultsParserUtil.toJSONObject(_toURLString(jsonFile));

        Assert.assertTrue(
            _equals(expectedJSONObject, actualJSONObject));
    }

    @Test
    public void testToString() throws Exception {
        assertToStringSample("sample-2.json");
        assertToStringSample("sample-3.json");
    }

    protected void assertToStringSample(String fileName) throws Exception {
        File jsonFile =    new File(_dependenciesDir, fileName);

        String expectedJSONString = _read(jsonFile);
        String actualJSONString = JenkinsResultsParserUtil.toString(
            _toURLString(jsonFile));

        // Remove carriage returns because the toString method handles them
        // differently but they don't have an impact on actual equality.

        expectedJSONString = expectedJSONString.replace("\n", "");
        actualJSONString = actualJSONString.replace("\n", "");

        Assert.assertEquals(expectedJSONString, actualJSONString);
    }

    private static boolean _equals(JSONArray jsonArray1, JSONArray jsonArray2)
        throws Exception {

        if (((jsonArray1 == null) && (jsonArray2 != null)) ||
            ((jsonArray1 != null) && (jsonArray2 == null))) {
                return false;
        }

        if (jsonArray1.length() != jsonArray2.length()) {
            return false;
        }

        for (int i = 0; i<jsonArray1.length(); i++) {
            Object obj1Child = jsonArray1.get(i);
            Object obj2Child = jsonArray2.get(i);

            if (!_equalsJSONChildObjectHandler(obj1Child, obj2Child)) {
                return false;
            }
        }

        return true;
    }

    private static boolean _equals(JSONObject jsonObj1, JSONObject jsonObj2)
        throws Exception {

        if (((jsonObj1 == null) && (jsonObj2 != null)) ||
            ((jsonObj1 != null) && (jsonObj2 == null))) {
                return false;
        }

        if (jsonObj1.length() != jsonObj2.length()) {
            return false;
        }

        Iterator<String> iterator = jsonObj1.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();

            Object obj1Child = jsonObj1.get(key);
            Object obj2Child = jsonObj2.get(key);

            if (!_equalsJSONChildObjectHandler(obj1Child, obj2Child)) {
                return false;
            }
        }

        return true;
    }

    private static boolean _equalsJSONChildObjectHandler(
        Object obj1, Object obj2)
            throws
                Exception {

        if ((obj1 == null) && (obj2 == null)) {
            return true;
        }

        if (((obj1 == null) && (obj2 != null)) ||
            ((obj1 != null) && (obj2 == null))) {

            return false;
        }

        if (obj1.getClass() != obj2.getClass()) {
            return false;
        }

        if (obj1 instanceof JSONObject) {
            return _equals((JSONObject)obj1, (JSONObject)obj2);
        }

        if (obj1 instanceof JSONArray) {
            return _equals((JSONArray)obj1, (JSONArray)obj2);
        }

        return obj1.equals(obj2);
    }

    private static String _read(File file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    private static String _toURLString(File file) throws Exception {
        URI uri = file.toURI();

        URL url = uri.toURL();

        return url.toString();
    }

    private static final File _dependenciesDir = new File(
        "src/test/resources/com/liferay/results/parser/dependencies/" +
        "JenkinsResultsParserUtilTest");

}