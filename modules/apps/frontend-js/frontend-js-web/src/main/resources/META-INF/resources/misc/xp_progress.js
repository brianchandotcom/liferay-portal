/* eslint-disable notice/notice */

// xp_progressbar
// Copyright 2004 Brian Gosselin of ScriptAsylum.com
//
// v1.0 - Initial release
// v1.1 - Added ability to pause the scrolling action (requires you to assign
//        the bar to a unique arbitrary variable).
//      - Added ability to specify an action to perform after a x amount of
//      - bar scrolls. This requires two added arguments.
// v1.2 - Added ability to hide/show each bar (requires you to assign the bar
//        to a unique arbitrary variable).

// var xyz = createBar(
// total_width,
// total_height,
// background_color,
// border_width,
// border_color,
// block_color,
// scroll_speed,
// block_count,
// scroll_count,
// action_to_perform_after_scrolled_n_times
// )

var w3c = !!document.getElementById;
var ie = !!document.all;
var N = -1;

function createBar(w, h, bgc, brdW, brdC, blkC, speed, blocks, count, action) {
	if (ie || w3c) {
		var t =
			'<div id="_xpbar' +
			++N +
			'" style="visibility:visible; position:relative; overflow:hidden; width:' +
			w +
			'px; height:' +
			h +
			'px; background-color:' +
			bgc +
			'; border-color:' +
			brdC +
			'; border-width:' +
			brdW +
			'px; border-style:solid; font-size:1px;">';
		t +=
			'<span id="blocks' +
			N +
			'" style="left:-' +
			(h * 2 + 1) +
			'px; position:absolute; font-size:1px">';
		for (i = 0; i < blocks; i++) {
			t +=
				'<span style="background-color:' +
				blkC +
				'; left:-' +
				(h * i + i) +
				'px; font-size:1px; position:absolute; width:' +
				h +
				'px; height:' +
				h +
				'px; ';
			t += ie
				? 'filter:alpha(opacity=' + (100 - i * (100 / blocks)) + ')'
				: '-Moz-opacity:' + (100 - i * (100 / blocks)) / 100;
			t += '"></span>';
		}
		t += '</span></div>';
		document.write(t);
		var bA = ie
			? document.all['blocks' + N]
			: document.getElementById('blocks' + N);
		bA.bar = ie
			? document.all['_xpbar' + N]
			: document.getElementById('_xpbar' + N);
		bA.blocks = blocks;
		bA.N = N;
		bA.w = w;
		bA.h = h;
		bA.speed = speed;
		bA.ctr = 0;
		bA.count = count;
		bA.action = action;
		bA.togglePause = togglePause;
		bA.showBar = function() {
			this.bar.style.visibility = 'visible';
		};
		bA.hideBar = function() {
			this.bar.style.visibility = 'hidden';
		};
		bA.tid = setInterval('startBar(' + N + ')', speed);
		return bA;
	}
}

function startBar(bn) {
	var t = ie
		? document.all['blocks' + bn]
		: document.getElementById('blocks' + bn);
	if (parseInt(t.style.left) + t.h + 1 - (t.blocks * t.h + t.blocks) > t.w) {
		t.style.left = -(t.h * 2 + 1) + 'px';
		t.ctr++;
		if (t.ctr >= t.count) {
			eval(t.action);
			t.ctr = 0;
		}
	}
	else {
		t.style.left = parseInt(t.style.left) + t.h + 1 + 'px';
	}
}

function togglePause() {
	if (this.tid == 0) {
		this.tid = setInterval('startBar(' + this.N + ')', this.speed);
	}
	else {
		clearInterval(this.tid);
		this.tid = 0;
	}
}
