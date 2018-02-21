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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Dennis Ju
 * @author Brian Wing Shun Chan
 */
public class TreeNodeView {

	public TreeNodeView(int id) {
		_id = id;
	}

	public void addChild(TreeNodeView treeNodeView) {
		_children.add(treeNodeView);
	}

	public List<TreeNodeView> getChildren() {
		return _children;
	}

	public int getDepth() {
		return _depth;
	}

	public String getHref() {
		return _href;
	}

	public long getId() {
		return _id;
	}

	public String getImg() {
		return _img;
	}

	public String getLs() {
		return _ls;
	}

	public String getName() {
		return _name;
	}

	public String getObjId() {
		return _objId;
	}

	public long getParentId() {
		return _parentId;
	}

	public boolean isLeaf() {
		return _leaf;
	}

	public void setChildren(List<TreeNodeView> children) {
		_children = children;
	}

	public void setDepth(int depth) {
		_depth = depth;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setImg(String img) {
		_img = img;
	}

	public void setLeaf(boolean leaf) {
		_leaf = leaf;
	}

	public void setLs(String ls) {
		_ls = ls;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setObjId(String objId) {
		_objId = objId;
	}

	public void setParentId(long parentId) {
		_parentId = parentId;
	}

	private List<TreeNodeView> _children = new ArrayList<>();
	private int _depth;
	private String _href = "javascript:;";
	private long _id;
	private String _img = StringPool.BLANK;
	private boolean _leaf;
	private String _ls = StringPool.BLANK;
	private String _name = StringPool.BLANK;
	private String _objId = StringPool.BLANK;
	private long _parentId;

}