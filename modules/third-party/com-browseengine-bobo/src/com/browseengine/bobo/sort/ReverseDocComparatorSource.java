/* @generated */
/*
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

package com.browseengine.bobo.sort;

import java.io.IOException;
import java.io.Serializable;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.ScoreDoc;

public class ReverseDocComparatorSource extends DocComparatorSource {
	private final DocComparatorSource _inner;
	public ReverseDocComparatorSource(DocComparatorSource inner) {
		_inner = inner;
	}

	@Override
	public DocComparator getComparator(IndexReader reader, int docbase)
			throws IOException {
		return new ReverseDocComparator(_inner.getComparator(reader, docbase));
	}

	public static class ReverseDocComparator extends DocComparator{
		private final DocComparator _comparator;
		public ReverseDocComparator(DocComparator comparator){
			_comparator = comparator;
		}

		@Override
		public int compare(ScoreDoc doc1, ScoreDoc doc2) {
			return -_comparator.compare(doc1, doc2);
		}

		@Override
		public Comparable value(final ScoreDoc doc) {
			return new ReverseComparable(_comparator.value(doc));

		}

		public static class ReverseComparable implements Comparable,Serializable{
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			final Comparable _inner;

			ReverseComparable(Comparable inner){
				_inner = inner;
			}

			public int compareTo(Object o) {
				if (o instanceof ReverseComparable){
					Comparable inner = ((ReverseComparable)o)._inner;
					return -_inner.compareTo(inner);
				}
				else{
					throw new IllegalStateException("expected instanace of "+ReverseComparable.class);
				}
			}

			@Override
			public String toString(){
				StringBuilder buf = new StringBuilder();
				buf.append("!").append(_inner);
				return buf.toString();
			}
		}

	}

}