/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.responsive.image.ImageResource;
import com.liferay.responsive.image.ResponsiveImage;
import com.liferay.responsive.image.ResponsiveImageCandidate;
import com.liferay.responsive.image.ResponsiveImageRequest;
import com.liferay.responsive.image.ResponsiveImageSource;
import com.liferay.responsive.image.ResponsiveImageURLTransformer;
import com.liferay.responsive.image.ResponsiveImageURLTransformerProvider;
import com.liferay.responsive.image.configuration.ResponsiveImageConfiguration;
import com.liferay.responsive.image.internal.configuration.ResponsiveImageConfigurationHelper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_mockImageURL(_RESOURCE_PATH);
		_mockMimeType("image/jpeg");

		Mockito.when(
			_responsiveImageConfigurationHelper.getCompanyId(
				Mockito.any(ResponsiveImageRequest.class))
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			_responsiveImageConfigurationHelper.getResponsiveImageConfiguration(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			_responsiveImageConfiguration
		);

		Mockito.when(
			_responsiveImageConfigurationHelper.getScopeKey(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_responsiveImageConfiguration.candidateWidths()
		).thenReturn(
			new String[] {"320", "640", "1280"}
		);

		Mockito.when(
			_responsiveImageConfiguration.defaultTransformations()
		).thenReturn(
			new String[] {"disable=upscale"}
		);

		_mockEnabled(true);

		Mockito.when(
			_responsiveImageConfiguration.mediaConditions()
		).thenReturn(
			new String[] {
				_MEDIA_CONDITION_NAME + ".query=" +
					RandomTestUtil.randomString()
			}
		);

		_mockResponsiveImageURLTransformerName(_TRANSFORMER_NAME);

		Mockito.when(
			_responsiveImageURLTransformerProvider.
				getResponsiveImageURLTransformer(_TRANSFORMER_NAME)
		).thenReturn(
			_responsiveImageURLTransformer
		);

		Mockito.when(
			_responsiveImageURLTransformer.transform(
				Mockito.anyString(), Mockito.anyMap())
		).thenAnswer(
			invocation -> {
				String url = invocation.getArgument(0);

				Map<String, String> imageTransformations = new TreeMap<>(
					(Map<String, String>)invocation.getArgument(1));

				if (imageTransformations.isEmpty()) {
					return url;
				}

				StringBundler sb = new StringBundler();

				sb.append(url);

				String delimiter = StringPool.QUESTION;

				for (Map.Entry<String, String> entry :
						imageTransformations.entrySet()) {

					sb.append(delimiter);
					sb.append(entry.getKey());
					sb.append(StringPool.EQUAL);
					sb.append(StringUtil.replace(entry.getValue(), ':', "%3A"));

					delimiter = StringPool.AMPERSAND;
				}

				return sb.toString();
			}
		);

		_responsiveImageFactory = new ResponsiveImageFactory(
			_portal, _responsiveImageConfigurationHelper,
			_responsiveImageURLTransformerProvider);
	}

	@Test
	public void testCreateCandidatesCarryTransformationsAndWidth() {
		_mockPresets(
			StringBundler.concat(
				_PRESET_NAME, ".", _MEDIA_CONDITION_NAME, ".sizes=100vw"),
			StringBundler.concat(
				_PRESET_NAME, ".", _MEDIA_CONDITION_NAME,
				".transformations=crop=1:1"));

		List<ResponsiveImageCandidate> responsiveImageCandidates =
			_getFirstSourceCandidates();

		Assert.assertEquals(
			responsiveImageCandidates.toString(), 3,
			responsiveImageCandidates.size());

		ResponsiveImageCandidate responsiveImageCandidate =
			responsiveImageCandidates.get(0);

		Assert.assertEquals(
			_RESOURCE_PATH + "?crop=1%3A1&disable=upscale&width=320",
			responsiveImageCandidate.getURL());
		Assert.assertEquals(
			Integer.valueOf(320), responsiveImageCandidate.getWidth());
	}

	@Test
	public void testCreateConfiguredHostIsPreferredOverTheCompanyCDNHost() {
		_mockCDNHost(_CDN_HOST);
		_mockCompanyCDNHostHttps(RandomTestUtil.randomString());
		_mockPresets();

		Assert.assertEquals(
			_CDN_HOST + _RESOURCE_PATH + "?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateConfiguredHostTrailingSlashIsStripped() {
		_mockCDNHost(_CDN_HOST + "/");
		_mockPresets();

		Assert.assertEquals(
			_CDN_HOST + _RESOURCE_PATH + "?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateConfiguredNameSelectsTheResponsiveImageURLTransformer() {
		_mockPresets();
		_getFirstSourceCandidates();

		Mockito.verify(
			_responsiveImageURLTransformer, Mockito.atLeastOnce()
		).transform(
			Mockito.anyString(), Mockito.anyMap()
		);
	}

	@Test
	public void testCreateDeclinesAProtocolRelativeImageAnotherHostServes() {
		_mockCDNHost(_CDN_HOST);
		_mockImageURL("//external.example.com/photo.jpg");
		_mockPresets();

		Assert.assertNull(
			_responsiveImageFactory.create(_responsiveImageRequest));
	}

	@Test
	public void testCreateDeclinesAnImageTheConfiguredHostDoesNotServe() {
		_mockCDNHost(_CDN_HOST);
		_mockImageURL(_THIRD_PARTY_HOST + _RESOURCE_PATH);
		_mockPresets();

		Assert.assertNull(
			_responsiveImageFactory.create(_responsiveImageRequest));
	}

	@Test
	public void testCreateDeclinesSVG() {
		_mockMimeType("image/svg+xml");

		Assert.assertNull(
			_responsiveImageFactory.create(_responsiveImageRequest));
	}

	@Test
	public void testCreateDeclinesUnknownMimeType() {
		_mockMimeType(null);

		Assert.assertNull(
			_responsiveImageFactory.create(_responsiveImageRequest));
	}

	@Test
	public void testCreateDeclinesWhenNoResponsiveImageURLTransformerIsDeployed()
		throws Exception {

		_mockPresets();
		_mockResponsiveImageURLTransformerName(RandomTestUtil.randomString());

		Assert.assertNull(
			_responsiveImageFactory.create(_responsiveImageRequest));

		Mockito.verify(
			_responsiveImageURLTransformer, Mockito.never()
		).transform(
			Mockito.anyString(), Mockito.anyMap()
		);
	}

	@Test
	public void testCreateDeclinesWhenTheConfigurationIsDisabled()
		throws Exception {

		_mockEnabled(false);
		_mockPresets();

		Assert.assertNull(
			_responsiveImageFactory.create(_responsiveImageRequest));

		Mockito.verify(
			_responsiveImageURLTransformer, Mockito.never()
		).transform(
			Mockito.anyString(), Mockito.anyMap()
		);
	}

	@Test
	public void testCreateFallsBackToTheCompanyHTTPCDNHost() {
		_mockCompanyCDNHostHttp(_COMPANY_CDN_HOST_HTTP);
		_mockPresets();

		Assert.assertEquals(
			_COMPANY_CDN_HOST_HTTP + _RESOURCE_PATH +
				"?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateFallsBackToTheCompanyHTTPSCDNHost() {
		_mockCompanyCDNHostHttp(_COMPANY_CDN_HOST_HTTP);
		_mockCompanyCDNHostHttps(_COMPANY_CDN_HOST_HTTPS);
		_mockPresets();

		Assert.assertEquals(
			_COMPANY_CDN_HOST_HTTPS + _RESOURCE_PATH +
				"?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateImageNamingADomainThePortalDoesNotServeIsDeclined() {
		_mockCDNHost(_CDN_HOST);
		_mockImageURL("https://" + _VIRTUAL_HOST + _RESOURCE_PATH);
		_mockPresets();

		Assert.assertNull(
			_responsiveImageFactory.create(_responsiveImageRequest));
	}

	@Test
	public void testCreateImageNamingAPortalDomainUsesTheConfiguredHost() {
		_mockCDNHost(_CDN_HOST);
		_mockImageURL("https://" + _VIRTUAL_HOST + _RESOURCE_PATH);
		_mockPresets();

		Mockito.when(
			_portal.isValidPortalDomain(_COMPANY_ID, _VIRTUAL_HOST)
		).thenReturn(
			true
		);

		Assert.assertEquals(
			_CDN_HOST + _RESOURCE_PATH + "?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateImageNamingItsOwnHostIsLeftAlone() {
		_mockImageURL(_THIRD_PARTY_HOST + _RESOURCE_PATH);
		_mockPresets();

		Assert.assertEquals(
			_THIRD_PARTY_HOST + _RESOURCE_PATH + "?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateImageNamingTheCompanyCDNHostUsesTheConfiguredHost() {
		_mockCDNHost(_CDN_HOST);
		_mockCompanyCDNHostHttps(_COMPANY_CDN_HOST_HTTPS);
		_mockImageURL(_COMPANY_CDN_HOST_HTTPS + _RESOURCE_PATH);
		_mockPresets();

		Assert.assertEquals(
			_CDN_HOST + _RESOURCE_PATH + "?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateImageNamingTheCompanyHTTPCDNHostIsLeftAlone() {
		_mockCompanyCDNHostHttp(_COMPANY_CDN_HOST_HTTP);
		_mockImageURL(_COMPANY_CDN_HOST_HTTP + _RESOURCE_PATH);
		_mockPresets();

		Assert.assertEquals(
			_COMPANY_CDN_HOST_HTTP + _RESOURCE_PATH +
				"?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateImageNamingTheCompanyHTTPSCDNHostIsLeftAlone() {
		_mockCompanyCDNHostHttps(_COMPANY_CDN_HOST_HTTPS);
		_mockImageURL(_COMPANY_CDN_HOST_HTTPS + _RESOURCE_PATH);
		_mockPresets();

		Assert.assertEquals(
			_COMPANY_CDN_HOST_HTTPS + _RESOURCE_PATH +
				"?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateImageNamingTheConfiguredHostIsLeftAlone() {
		_mockCDNHost(_CDN_HOST);
		_mockImageURL(_CDN_HOST + _RESOURCE_PATH);
		_mockPresets();

		Assert.assertEquals(
			_CDN_HOST + _RESOURCE_PATH + "?disable=upscale&width=320",
			_getURL(_getFirstSourceCandidates(), 0));
	}

	@Test
	public void testCreateMaxWidthTruncatesLadderKeepingTheBoundaryWidth() {
		_mockPresets(
			_PRESET_NAME + ".default.sizes=500px",
			_PRESET_NAME + ".default.maxWidth=500");

		List<ResponsiveImageCandidate> responsiveImageCandidates =
			_getFirstSourceCandidates();

		Assert.assertEquals(
			responsiveImageCandidates.toString(), 2,
			responsiveImageCandidates.size());
		Assert.assertEquals(
			Integer.valueOf(320), _getWidth(responsiveImageCandidates, 0));
		Assert.assertEquals(
			Integer.valueOf(640), _getWidth(responsiveImageCandidates, 1));
	}

	@Test
	public void testCreateWithoutAHttpServletRequest() {
		_mockPresets();

		ResponsiveImage responsiveImage = _responsiveImageFactory.create(
			ResponsiveImageRequest.of(_imageResource));

		List<ResponsiveImageSource> responsiveImageSources =
			responsiveImage.getSources();

		Assert.assertFalse(
			String.valueOf(responsiveImageSources),
			responsiveImageSources.isEmpty());
	}

	private List<ResponsiveImageCandidate> _getFirstSourceCandidates() {
		ResponsiveImage responsiveImage = _responsiveImageFactory.create(
			_responsiveImageRequest);

		List<ResponsiveImageSource> responsiveImageSources =
			responsiveImage.getSources();

		ResponsiveImageSource responsiveImageSource =
			responsiveImageSources.get(0);

		return responsiveImageSource.getCandidates();
	}

	private String _getURL(
		List<ResponsiveImageCandidate> responsiveImageCandidates, int index) {

		ResponsiveImageCandidate responsiveImageCandidate =
			responsiveImageCandidates.get(index);

		return responsiveImageCandidate.getURL();
	}

	private Integer _getWidth(
		List<ResponsiveImageCandidate> responsiveImageCandidates, int index) {

		ResponsiveImageCandidate responsiveImageCandidate =
			responsiveImageCandidates.get(index);

		return responsiveImageCandidate.getWidth();
	}

	private void _mockCDNHost(String cdnHost) {
		Mockito.when(
			_responsiveImageConfiguration.cdnHost()
		).thenReturn(
			cdnHost
		);
	}

	private void _mockCompanyCDNHostHttp(String companyCDNHostHttp) {
		Mockito.when(
			_portal.getCDNHostHttp(_COMPANY_ID)
		).thenReturn(
			companyCDNHostHttp
		);
	}

	private void _mockCompanyCDNHostHttps(String companyCDNHostHttps) {
		Mockito.when(
			_portal.getCDNHostHttps(_COMPANY_ID)
		).thenReturn(
			companyCDNHostHttps
		);
	}

	private void _mockEnabled(boolean enabled) {
		Mockito.when(
			_responsiveImageConfiguration.enabled()
		).thenReturn(
			enabled
		);
	}

	private void _mockImageURL(String imageURL) {
		Mockito.when(
			_imageResource.getURL()
		).thenReturn(
			imageURL
		);
	}

	private void _mockMimeType(String mimeType) {
		Mockito.when(
			_imageResource.getMimeType()
		).thenReturn(
			mimeType
		);
	}

	private void _mockPresets() {
		_mockPresets(_PRESET_NAME + ".default.sizes=100vw");
	}

	private void _mockPresets(String... presets) {
		Mockito.when(
			_responsiveImageConfiguration.presets()
		).thenReturn(
			presets
		);
	}

	private void _mockResponsiveImageURLTransformerName(
		String responsiveImageURLTransformerName) {

		Mockito.when(
			_responsiveImageConfiguration.responsiveImageURLTransformerName()
		).thenReturn(
			responsiveImageURLTransformerName
		);
	}

	private static final String _CDN_HOST = "https://images.example.com";

	private static final String _COMPANY_CDN_HOST_HTTP =
		"http://cdn.example.com";

	private static final String _COMPANY_CDN_HOST_HTTPS =
		"https://cdn.example.com";

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _MEDIA_CONDITION_NAME = StringUtil.removeChar(
		RandomTestUtil.randomString(), CharPool.PERIOD);

	private static final String _PRESET_NAME = StringUtil.removeChar(
		RandomTestUtil.randomString(), CharPool.PERIOD);

	private static final String _RESOURCE_PATH = "/documents/1/2/photo.jpg";

	private static final String _THIRD_PARTY_HOST =
		"https://external.example.com";

	private static final String _TRANSFORMER_NAME =
		RandomTestUtil.randomString();

	private static final String _VIRTUAL_HOST = "www.example.com";

	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final ImageResource _imageResource = Mockito.mock(
		ImageResource.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final ResponsiveImageConfiguration _responsiveImageConfiguration =
		Mockito.mock(ResponsiveImageConfiguration.class);
	private final ResponsiveImageConfigurationHelper
		_responsiveImageConfigurationHelper = Mockito.mock(
			ResponsiveImageConfigurationHelper.class);
	private ResponsiveImageFactory _responsiveImageFactory;
	private final ResponsiveImageRequest _responsiveImageRequest =
		ResponsiveImageRequest.builder(
			_imageResource
		).httpServletRequest(
			_httpServletRequest
		).presetName(
			_PRESET_NAME
		).build();
	private final ResponsiveImageURLTransformer _responsiveImageURLTransformer =
		Mockito.mock(ResponsiveImageURLTransformer.class);

	@SuppressWarnings("unchecked")
	private final ResponsiveImageURLTransformerProvider
		_responsiveImageURLTransformerProvider = Mockito.mock(
			ResponsiveImageURLTransformerProvider.class);

}