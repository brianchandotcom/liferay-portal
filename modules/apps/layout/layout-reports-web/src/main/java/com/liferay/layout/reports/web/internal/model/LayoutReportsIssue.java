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

package com.liferay.layout.reports.web.internal.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * @author Cristina González
 */
public class LayoutReportsIssue {

	public LayoutReportsIssue(List<Detail> details, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		if (details == null) {
			_details = Collections.emptyList();
		}
		else {
			_details = Collections.unmodifiableList(details);
		}

		_key = key;

		Stream<Detail> stream = _details.stream();

		_total = stream.mapToLong(
			Detail::getTotal
		).sum();
	}

	@Override
	public boolean equals(Object object) {
		LayoutReportsIssue layoutReportsIssue = (LayoutReportsIssue)object;

		if (Objects.equals(layoutReportsIssue._details, _details) &&
			Objects.equals(layoutReportsIssue._key, _key) &&
			(layoutReportsIssue._total == _total)) {

			return true;
		}

		return false;
	}

	public List<Detail> getDetails() {
		return _details;
	}

	public Key getKey() {
		return _key;
	}

	public long getTotal() {
		return _total;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _key);

		return HashUtil.hash(hashCode, _total);
	}

	public JSONObject toJSONObject(ResourceBundle resourceBundle) {
		Stream<Detail> stream = _details.stream();

		return JSONUtil.put(
			"details",
			JSONUtil.putAll(
				stream.filter(
					detail -> detail.getTotal() > 0
				).map(
					detail -> detail.toJSONObject(resourceBundle)
				).toArray())
		).put(
			"key", _key.toString()
		).put(
			"title",
			ResourceBundleUtil.getString(resourceBundle, _key.toString())
		).put(
			"total", _total
		);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler((7 * _details.size()) + 3);

		sb.append("{details={");

		for (int i = 0; i < _details.size(); i++) {
			Detail detail = _details.get(i);

			if (detail.getTotal() > 0) {
				sb.append(detail.getKey());
				sb.append("=");
				sb.append(detail.getTotal());

				if (i < (_details.size() - 1)) {
					sb.append(", ");
				}
			}
		}

		sb.append("}, key=");
		sb.append(_key);
		sb.append(", total=");
		sb.append(_total);
		sb.append("}");

		return sb.toString();
	}

	public static class Detail {

		public Detail(Detail.Key key, long total) {
			if (key == null) {
				throw new IllegalArgumentException("Key is null");
			}

			_key = key;
			_total = total;
		}

		@Override
		public boolean equals(Object object) {
			Detail detail = (Detail)object;

			if ((detail._key == _key) && (detail._total == _total)) {
				return true;
			}

			return false;
		}

		public Key getKey() {
			return _key;
		}

		public long getTotal() {
			return _total;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _key);

			return HashUtil.hash(hashCode, _total);
		}

		public JSONObject toJSONObject(ResourceBundle resourceBundle) {
			return JSONUtil.put(
				"key", _key.toString()
			).put(
				"title",
				ResourceBundleUtil.getString(resourceBundle, _key.toString())
			).put(
				"total", _total
			);
		}

		@Override
		public String toString() {
			JSONObject jsonObject = toJSONObject(
				ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE);

			return jsonObject.toString();
		}

		public enum Key {

			CANONICAL_LINK {

				@Override
				public String toString() {
					return "canonical-link";
				}

			},
			CRAWLABLE_ANCHORS {

				@Override
				public String toString() {
					return "crawlable-anchors";
				}

			},
			FONT_SIZES {

				@Override
				public String toString() {
					return "font-sizes";
				}

			},
			HREFLANG {

				@Override
				public String toString() {
					return "hreflang";
				}

			},
			INCORRECT_IMAGE_ASPECT_RATIOS {

				@Override
				public String toString() {
					return "incorrect-image-aspect-ratios";
				}

			},
			INDEXING {

				@Override
				public String toString() {
					return "indexing";
				}

			},
			LINKS_DO_NOT_HAVE_DESCRIPTIVE_TEXT {

				@Override
				public String toString() {
					return "links-do-not-have-descriptive-text";
				}

			},
			LOW_CONTRAST_RATIO {

				@Override
				public String toString() {
					return "low-contrast-ratio";
				}

			},
			META_DESCRIPTION_IS_MISSING {

				@Override
				public String toString() {
					return "meta-description-is-missing";
				}

			},
			MISSING_IMG_ALT_ATTRIBUTES {

				@Override
				public String toString() {
					return "missing-img-alt-attributes";
				}

			},
			MISSING_INPUT_ALT_ATTRIBUTES {

				@Override
				public String toString() {
					return "missing-input-alt-attributes";
				}

			},
			MISSING_VIDEO_CAPTION {

				@Override
				public String toString() {
					return "missing-video-caption";
				}

			},
			SMALL_TAP_TARGETS {

				@Override
				public String toString() {
					return "small-tap-targets";
				}

			},
			TITLE {

				@Override
				public String toString() {
					return "title";
				}

			}

		}

		private final Detail.Key _key;
		private final long _total;

	}

	public enum Key {

		ACCESSIBILITY {

			@Override
			public String toString() {
				return "accessibility";
			}

		},
		SEO {

			@Override
			public String toString() {
				return "seo";
			}

		},

	}

	private final List<Detail> _details;
	private final Key _key;
	private final long _total;

}