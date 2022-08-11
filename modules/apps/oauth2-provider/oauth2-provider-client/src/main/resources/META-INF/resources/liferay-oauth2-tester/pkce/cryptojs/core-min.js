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

var CryptoJS =
	CryptoJS ||
	(function (h, r) {
		const k = {};
		const l = (k.lib = {});
		const n = function () {};
		const f = (l.Base = {
			extend(a) {
				n.prototype = this;
				const b = new n();
				a && b.mixIn(a);
				b.hasOwnProperty('init') ||
					(b.init = function () {
						b.$super.init.apply(this, arguments);
					});
				b.init.prototype = b;
				b.$super = this;

				return b;
			},
			create() {
				const a = this.extend();
				a.init.apply(a, arguments);

				return a;
			},
			init() {},
			mixIn(a) {
				for (const b in a) {
					a.hasOwnProperty(b) && (this[b] = a[b]);
				}
				a.hasOwnProperty('toString') && (this.toString = a.toString);
			},
			clone() {
				return this.init.prototype.extend(this);
			},
		});
		var j = (l.WordArray = f.extend({
			init(a, b) {
				a = this.words = a || [];
				this.sigBytes = b != r ? b : 4 * a.length;
			},
			toString(a) {
				return (a || s).stringify(this);
			},
			concat(a) {
				const b = this.words;
				const d = a.words;
				const c = this.sigBytes;
				a = a.sigBytes;
				this.clamp();
				if (c % 4) {
					for (var e = 0; e < a; e++) {
						b[(c + e) >>> 2] |=
							((d[e >>> 2] >>> (24 - 8 * (e % 4))) & 255) <<
							(24 - 8 * ((c + e) % 4));
					}
				}
				else if (65535 < d.length) {
					for (e = 0; e < a; e += 4) {
						b[(c + e) >>> 2] = d[e >>> 2];
					}
				}
				else {
					b.push.apply(b, d);
				}
				this.sigBytes += a;

				return this;
			},
			clamp() {
				const a = this.words;
				const b = this.sigBytes;
				a[b >>> 2] &= 4294967295 << (32 - 8 * (b % 4));
				a.length = h.ceil(b / 4);
			},
			clone() {
				const a = f.clone.call(this);
				a.words = this.words.slice(0);

				return a;
			},
			random(a) {
				for (var b = [], d = 0; d < a; d += 4) {
					b.push((4294967296 * h.random()) | 0);
				}

				return new j.init(b, a);
			},
		}));
		const m = (k.enc = {});
		var s = (m.Hex = {
			stringify(a) {
				const b = a.words;
				a = a.sigBytes;
				for (var d = [], c = 0; c < a; c++) {
					const e = (b[c >>> 2] >>> (24 - 8 * (c % 4))) & 255;
					d.push((e >>> 4).toString(16));
					d.push((e & 15).toString(16));
				}

				return d.join('');
			},
			parse(a) {
				for (var b = a.length, d = [], c = 0; c < b; c += 2) {
					d[c >>> 3] |=
						parseInt(a.substr(c, 2), 16) << (24 - 4 * (c % 8));
				}

				return new j.init(d, b / 2);
			},
		});
		const p = (m.Latin1 = {
			stringify(a) {
				const b = a.words;
				a = a.sigBytes;
				for (var d = [], c = 0; c < a; c++) {
					d.push(
						String.fromCharCode(
							(b[c >>> 2] >>> (24 - 8 * (c % 4))) & 255
						)
					);
				}

				return d.join('');
			},
			parse(a) {
				for (var b = a.length, d = [], c = 0; c < b; c++) {
					d[c >>> 2] |= (a.charCodeAt(c) & 255) << (24 - 8 * (c % 4));
				}

				return new j.init(d, b);
			},
		});
		const t = (m.Utf8 = {
			stringify(a) {
				try {
					return decodeURIComponent(escape(p.stringify(a)));
				}
				catch (b) {
					throw Error('Malformed UTF-8 data');
				}
			},
			parse(a) {
				return p.parse(unescape(encodeURIComponent(a)));
			},
		});
		const q = (l.BufferedBlockAlgorithm = f.extend({
			reset() {
				this._data = new j.init();
				this._nDataBytes = 0;
			},
			_append(a) {
				'string' === typeof a && (a = t.parse(a));
				this._data.concat(a);
				this._nDataBytes += a.sigBytes;
			},
			_process(a) {
				const b = this._data;
				const d = b.words;
				let c = b.sigBytes;
				const e = this.blockSize;
				var f = c / (4 * e);
				var f = a ? h.ceil(f) : h.max((f | 0) - this._minBufferSize, 0);
				a = f * e;
				c = h.min(4 * a, c);
				if (a) {
					for (var g = 0; g < a; g += e) {
						this._doProcessBlock(d, g);
					}
					g = d.splice(0, a);
					b.sigBytes -= c;
				}

				return new j.init(g, c);
			},
			clone() {
				const a = f.clone.call(this);
				a._data = this._data.clone();

				return a;
			},
			_minBufferSize: 0,
		}));
		l.Hasher = q.extend({
			cfg: f.extend(),
			init(a) {
				this.cfg = this.cfg.extend(a);
				this.reset();
			},
			reset() {
				q.reset.call(this);
				this._doReset();
			},
			update(a) {
				this._append(a);
				this._process();

				return this;
			},
			finalize(a) {
				a && this._append(a);

				return this._doFinalize();
			},
			blockSize: 16,
			_createHelper(a) {
				return function (b, d) {
					return new a.init(d).finalize(b);
				};
			},
			_createHmacHelper(a) {
				return function (b, d) {
					return new u.HMAC.init(a, d).finalize(b);
				};
			},
		});
		var u = (k.algo = {});

		return k;
	})(Math);
