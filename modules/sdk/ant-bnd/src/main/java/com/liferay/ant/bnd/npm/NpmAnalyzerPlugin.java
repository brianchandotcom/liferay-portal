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

package com.liferay.ant.bnd.npm;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.AnalyzerPlugin;
import aQute.bnd.version.Version;

import aQute.lib.json.Decoder;
import aQute.lib.json.JSONCodec;

import java.io.InputStream;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raymond Augé
 */
public class NpmAnalyzerPlugin implements AnalyzerPlugin {

	public static final String WEB_CONTEXT_PATH = "Web-ContextPath";

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		Jar jar = analyzer.getJar();

		Resource npmJSONResource = jar.getResource("package.json");

		if (npmJSONResource == null) {
			return false;
		}

		NpmModule npmModule = processNpmJsonResource(analyzer, npmJSONResource);

		processDependencies(analyzer, npmModule);

		return false;
	}

	public static class NpmModule {

		public Map<String, String> dependencies;
		public String name;
		public Map<String, String> runtime;
		public String version;

	}

	protected void appendInclusive(
		StringBuilder sb, String group1, String group2) {

		sb.append("(&(version>=");

		Matcher matcher = _versionNamedPattern.matcher(group1);

		matcher.matches();

		String major = matcher.group("major");
		String minor = matcher.group("minor");
		String micro = matcher.group("micro");
		String qualifier = matcher.group("qualifier");

		sb.append(toVersion(major, minor, micro, qualifier));

		matcher = _versionNamedPattern.matcher(group2);

		matcher.matches();

		major = matcher.group("major");
		minor = matcher.group("minor");
		micro = matcher.group("micro");
		qualifier = matcher.group("qualifier");

		if (minor == null) {
			major = Integer.parseInt(major) + 1 + "";

			sb.append(")(!(version>=");
			sb.append(major);
			sb.append(".0.0)");
		}
		else if (micro == null) {
			sb.append(")(version<=");
			sb.append(major);
			sb.append(".");
			sb.append(Integer.parseInt(minor) + 1);
			sb.append(".0");
		}
		else {
			sb.append(")(version<=");
			sb.append(toVersion(major, minor, micro, qualifier));
		}

		sb.append("))");
	}

	protected void appendPrefixRange(
		StringBuilder sb, String prefix, String version) {

		Matcher matcher = _versionNamedPattern.matcher(version);

		matcher.matches();

		String major = matcher.group("major");
		String minor = matcher.group("minor");
		String micro = matcher.group("micro");
		String qualifier = matcher.group("qualifier");

		if (prefix.equals("v") || prefix.equals("=")) {
			sb.append("(version=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")");
		}
		else if (prefix.equals("<")) {
			sb.append("(!(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append("))");
		}
		else if (prefix.equals("<=")) {
			sb.append("(version<=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")");
		}
		else if (prefix.equals(">")) {
			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")))");
		}
		else if (prefix.equals(">=")) {
			sb.append("(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")");
		}
		else if (prefix.equals("~")) {
			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version>=");

			if (minor != null) {
				sb.append(major);
				sb.append(".");
				sb.append(Integer.parseInt(minor) + 1);
				sb.append(".0");
			}
			else {
				sb.append(Integer.parseInt(major) + 1);
				sb.append(".0.0");
			}

			sb.append(")))");
		}
		else if (prefix.equals("^")) {
			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version>=");

			if (!major.equals("0") || minor.equalsIgnoreCase("x") ||
				minor.equals("*")) {

				sb.append(Integer.parseInt(major) + 1);
				sb.append(".0.0");
			}
			else if (!minor.equals("0") || (micro == null) ||
					 micro.equalsIgnoreCase("x") || micro.equals("*")) {

				sb.append("0.");
				sb.append(Integer.parseInt(desugar(minor)) + 1);
				sb.append(".0");
			}
			else {
				sb.append("0.0.");
				sb.append(Integer.parseInt(desugar(micro)) + 1);
			}

			sb.append(")))");
		}
	}

	protected void appendRange(StringBuilder sb, String group1, String group2) {
		sb.append("(&");

		Matcher matcher = _versionPrefixRangePattern.matcher(group1);

		matcher.matches();

		appendPrefixRange(sb, matcher.group(1), matcher.group(2));

		matcher = _versionPrefixRangePattern.matcher(group2);

		matcher.matches();

		appendPrefixRange(sb, matcher.group(1), matcher.group(2));

		sb.append(")");
	}

	protected void appendVersion(StringBuilder sb, Matcher matcher) {
		String major = matcher.group("major");
		String minor = matcher.group("minor");
		String micro = matcher.group("micro");
		String qualifier = matcher.group("qualifier");

		if ((minor == null) || minor.equalsIgnoreCase("x") ||
			minor.equals("*")) {

			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version>=");
			sb.append(Integer.parseInt(major) + 1);
			sb.append(".0.0)))");
		}
		else if ((micro == null) || micro.equalsIgnoreCase("x") ||
				 micro.equals("*")) {

			sb.append("(&(version>=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")(!(version>=");
			sb.append(major);
			sb.append(".");
			sb.append(Integer.parseInt(minor) + 1);
			sb.append(".0)))");
		}
		else {
			sb.append("(version=");
			sb.append(toVersion(major, minor, micro, qualifier));
			sb.append(")");
		}
	}

	protected String desugar(String minor) {
		if ((minor == null) || minor.equalsIgnoreCase("x") ||
			minor.equals("*")) {

			return "0";
		}

		return minor;
	}

	protected NpmModule getNpmModule(InputStream inputStream) throws Exception {
		JSONCodec jsonCodec = new JSONCodec();

		Decoder decoder = jsonCodec.dec();

		decoder = decoder.from(inputStream);

		return decoder.get(NpmModule.class);
	}

	protected String getNpmVersionFilter(String version) {
		StringBuilder sb = new StringBuilder();

		String[] comparatorSets = version.split("\\|\\|");

		// Comparator sets are OR'd together

		if (comparatorSets.length > 1) {
			sb.append("(|");
		}

		for (String comparatorSet : comparatorSets) {
			comparatorSet = comparatorSet.trim();

			if ((comparatorSet.length() == 0) || comparatorSet.equals("*")) {
				comparatorSet = ">=0";
			}

			Matcher inclusiveMatcher = _versionInclusiveRangePattern.matcher(
				comparatorSet);
			Matcher rangeMatcher = _versionRangePattern.matcher(comparatorSet);
			Matcher prefixRangeMatcher = _versionPrefixRangePattern.matcher(
				comparatorSet);
			Matcher versionMatcher = _versionNamedPattern.matcher(
				comparatorSet);

			if (inclusiveMatcher.matches()) {
				appendInclusive(
					sb, inclusiveMatcher.group(1), inclusiveMatcher.group(9));
			}
			else if (rangeMatcher.matches()) {
				appendRange(sb, rangeMatcher.group(1), rangeMatcher.group(11));
			}
			else if (prefixRangeMatcher.matches()) {
				appendPrefixRange(
					sb, prefixRangeMatcher.group(1),
					prefixRangeMatcher.group(2));
			}
			else if (versionMatcher.matches()) {
				appendVersion(sb, versionMatcher);
			}
		}

		if (comparatorSets.length > 1) {
			sb.append(")");
		}

		return sb.toString();
	}

	protected void processDependencies(Analyzer analyzer, NpmModule npmModule) {
		if (npmModule.runtime == null) {
			return;
		}

		Parameters parameters = new Parameters();

		for (Entry<String, String> entry : npmModule.runtime.entrySet()) {
			Attrs attrs = new Attrs();

			StringBuilder sb = new StringBuilder();

			sb.append("(&(");
			sb.append(_OSGI_WEBRESOURCE);
			sb.append("=");

			String name = entry.getKey();

			sb.append(name);

			sb.append(")");

			String version = entry.getValue();

			sb.append(getNpmVersionFilter(version));

			sb.append(")");

			attrs.put(Constants.FILTER_DIRECTIVE, sb.toString());

			parameters.add(_OSGI_WEBRESOURCE, attrs);
		}

		setCapabilities(analyzer, Constants.REQUIRE_CAPABILITY, parameters);
	}

	protected NpmModule processNpmJsonResource(
			Analyzer analyzer, Resource npmJSONResource)
		throws Exception {

		NpmModule npmModule = getNpmModule(npmJSONResource.openInputStream());

		String bundleVersion = analyzer.getBundleVersion();

		if (bundleVersion == null) {
			Version version = null;

			try {
				version = new Version(npmModule.version);
			}
			catch (IllegalArgumentException iae) {
				String sanitizedQualifier = npmModule.version.replaceAll(
					"[^-_\\da-zA-Z]", "");

				version = new Version("0.0.0." + sanitizedQualifier);
			}

			analyzer.setBundleVersion(version.toString());
		}

		Parameters parameters = new Parameters();

		Attrs attrs = new Attrs();

		String npmName = npmModule.name;

		String webContextPath = analyzer.getProperty(WEB_CONTEXT_PATH);

		if ((webContextPath == null) && (npmName != null)) {
			if (npmName.indexOf('/') == 0) {
				npmName = npmName.substring(1);
			}

			analyzer.setProperty(
				WEB_CONTEXT_PATH,
				'/' + npmName + "-" + analyzer.getBundleVersion());
		}

		attrs.put(_OSGI_WEBRESOURCE, npmName);

		attrs.put(
			Constants.VERSION_ATTRIBUTE + ":Version",
			analyzer.getBundleVersion());

		parameters.add(_OSGI_WEBRESOURCE, attrs);

		setCapabilities(analyzer, Constants.PROVIDE_CAPABILITY, parameters);

		return npmModule;
	}

	protected void setCapabilities(
		Analyzer analyzer, String capabilityType, Parameters parameters) {

		if (parameters.isEmpty()) {
			return;
		}

		Parameters headerParameters = new Parameters(
			analyzer.getProperty(capabilityType));

		if (!headerParameters.isEmpty()) {
			parameters.mergeWith(headerParameters, false);
		}

		analyzer.setProperty(capabilityType, parameters.toString());
	}

	protected String toVersion(
		String major, String minor, String micro, String qualifier) {

		StringBuilder sb = new StringBuilder();

		sb.append(major);

		if ((minor == null) || minor.equalsIgnoreCase("x") ||
			minor.equals("*")) {

			sb.append(".0");
		}
		else {
			sb.append(".");
			sb.append(minor);
		}

		if ((micro == null) || micro.equalsIgnoreCase("x") ||
			micro.equals("*")) {

			sb.append(".0");
		}
		else {
			sb.append(".");
			sb.append(micro);
		}

		if (qualifier == null) {
			sb.append("");
		}
		else {
			sb.append(".");
			sb.append(qualifier);
		}

		return sb.toString();
	}

	private static final String _OSGI_WEBRESOURCE = "osgi.webresource";

	private static final String _VERSION =
		"((\\d{1,9})(\\.([\\dx\\*]{1,9})(\\.([\\dx\\*]{1,9})" +
			"([\\.-]([-_\\da-zA-Z]+))?)?)?)";

	private static final String _VERSION_INCLUSIVE_RANGE =
		_VERSION + "\\s*-\\s*" + _VERSION;

	private static final String _VERSION_NAMED =
		"(?<major>\\d{1,9})(\\.(?<minor>[\\dx\\*]{1,9})" +
			"(\\.(?<micro>[\\dx\\*]{1,9})" +
				"([\\.-](?<qualifier>[-_\\da-zA-Z]+))?)?)?";

	private static final String _VERSION_PREFIX_RANGE = "(<|<=|>|>=|=|~|\\^|v)";

	private static final String _VERSION_RANGE =
		"(" + _VERSION_PREFIX_RANGE + _VERSION + ")\\s+(" +
			_VERSION_PREFIX_RANGE + _VERSION + ")";

	private static final Pattern _versionInclusiveRangePattern =
		Pattern.compile(_VERSION_INCLUSIVE_RANGE);
	private static final Pattern _versionNamedPattern = Pattern.compile(
		_VERSION_NAMED);
	private static final Pattern _versionPrefixRangePattern = Pattern.compile(
		_VERSION_PREFIX_RANGE + _VERSION);
	private static final Pattern _versionRangePattern = Pattern.compile(
		_VERSION_RANGE);

}