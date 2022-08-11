/* eslint-disable */
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
	(function (h, s) {
		const f = {};
		const t = (f.lib = {});
		const g = function () {};
		const j = (t.Base = {
			extend(a) {
				g.prototype = this;
				const c = new g();
				a && c.mixIn(a);
				c.hasOwnProperty('init') ||
					(c.init = function () {
						c.$super.init.apply(this, arguments);
					});
				c.init.prototype = c;
				c.$super = this;

				return c;
			},
			create() {
				const a = this.extend();
				a.init.apply(a, arguments);

				return a;
			},
			init() {},
			mixIn(a) {
				for (const c in a) {
					a.hasOwnProperty(c) && (this[c] = a[c]);
				}
				a.hasOwnProperty('toString') && (this.toString = a.toString);
			},
			clone() {
				return this.init.prototype.extend(this);
			},
		});
		var q = (t.WordArray = j.extend({
			init(a, c) {
				a = this.words = a || [];
				this.sigBytes = c != s ? c : 4 * a.length;
			},
			toString(a) {
				return (a || u).stringify(this);
			},
			concat(a) {
				const c = this.words;
				const d = a.words;
				const b = this.sigBytes;
				a = a.sigBytes;
				this.clamp();
				if (b % 4) {
					for (var e = 0; e < a; e++) {
						c[(b + e) >>> 2] |=
							((d[e >>> 2] >>> (24 - 8 * (e % 4))) & 255) <<
							(24 - 8 * ((b + e) % 4));
					}
				}
				else if (65535 < d.length) {
					for (e = 0; e < a; e += 4) {
						c[(b + e) >>> 2] = d[e >>> 2];
					}
				}
				else {
					c.push.apply(c, d);
				}
				this.sigBytes += a;

				return this;
			},
			clamp() {
				const a = this.words;
				const c = this.sigBytes;
				a[c >>> 2] &= 4294967295 << (32 - 8 * (c % 4));
				a.length = h.ceil(c / 4);
			},
			clone() {
				const a = j.clone.call(this);
				a.words = this.words.slice(0);

				return a;
			},
			random(a) {
				for (var c = [], d = 0; d < a; d += 4) {
					c.push((4294967296 * h.random()) | 0);
				}

				return new q.init(c, a);
			},
		}));
		const v = (f.enc = {});
		var u = (v.Hex = {
			stringify(a) {
				const c = a.words;
				a = a.sigBytes;
				for (var d = [], b = 0; b < a; b++) {
					const e = (c[b >>> 2] >>> (24 - 8 * (b % 4))) & 255;
					d.push((e >>> 4).toString(16));
					d.push((e & 15).toString(16));
				}

				return d.join('');
			},
			parse(a) {
				for (var c = a.length, d = [], b = 0; b < c; b += 2) {
					d[b >>> 3] |=
						parseInt(a.substr(b, 2), 16) << (24 - 4 * (b % 8));
				}

				return new q.init(d, c / 2);
			},
		});
		const k = (v.Latin1 = {
			stringify(a) {
				const c = a.words;
				a = a.sigBytes;
				for (var d = [], b = 0; b < a; b++) {
					d.push(
						String.fromCharCode(
							(c[b >>> 2] >>> (24 - 8 * (b % 4))) & 255
						)
					);
				}

				return d.join('');
			},
			parse(a) {
				for (var c = a.length, d = [], b = 0; b < c; b++) {
					d[b >>> 2] |= (a.charCodeAt(b) & 255) << (24 - 8 * (b % 4));
				}

				return new q.init(d, c);
			},
		});
		const l = (v.Utf8 = {
			stringify(a) {
				try {
					return decodeURIComponent(escape(k.stringify(a)));
				}
				catch (c) {
					throw Error('Malformed UTF-8 data');
				}
			},
			parse(a) {
				return k.parse(unescape(encodeURIComponent(a)));
			},
		});
		const x = (t.BufferedBlockAlgorithm = j.extend({
			reset() {
				this._data = new q.init();
				this._nDataBytes = 0;
			},
			_append(a) {
				'string' === typeof a && (a = l.parse(a));
				this._data.concat(a);
				this._nDataBytes += a.sigBytes;
			},
			_process(a) {
				const c = this._data;
				const d = c.words;
				let b = c.sigBytes;
				const e = this.blockSize;
				var f = b / (4 * e);
				var f = a ? h.ceil(f) : h.max((f | 0) - this._minBufferSize, 0);
				a = f * e;
				b = h.min(4 * a, b);
				if (a) {
					for (var m = 0; m < a; m += e) {
						this._doProcessBlock(d, m);
					}
					m = d.splice(0, a);
					c.sigBytes -= b;
				}

				return new q.init(m, b);
			},
			clone() {
				const a = j.clone.call(this);
				a._data = this._data.clone();

				return a;
			},
			_minBufferSize: 0,
		}));
		t.Hasher = x.extend({
			cfg: j.extend(),
			init(a) {
				this.cfg = this.cfg.extend(a);
				this.reset();
			},
			reset() {
				x.reset.call(this);
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
				return function (c, d) {
					return new a.init(d).finalize(c);
				};
			},
			_createHmacHelper(a) {
				return function (c, d) {
					return new w.HMAC.init(a, d).finalize(c);
				};
			},
		});
		var w = (f.algo = {});

		return f;
	})(Math);
(function (h) {
	for (
		var s = CryptoJS,
			f = s.lib,
			t = f.WordArray,
			g = f.Hasher,
			f = s.algo,
			j = [],
			q = [],
			v = function (a) {
				return (4294967296 * (a - (a | 0))) | 0;
			},
			u = 2,
			k = 0;
		64 > k;

	) {
		var l;
		a: {
			l = u;
			for (let x = h.sqrt(l), w = 2; w <= x; w++) {
				if (!(l % w)) {
					l = !1;
					break a;
				}
			}
			l = !0;
		}
		l &&
			(8 > k && (j[k] = v(h.pow(u, 0.5))),
			(q[k] = v(h.pow(u, 1 / 3))),
			k++);
		u++;
	}
	const a = [];
	var f = (f.SHA256 = g.extend({
		_doReset() {
			this._hash = new t.init(j.slice(0));
		},
		_doProcessBlock(c, d) {
			for (
				var b = this._hash.words,
					e = b[0],
					f = b[1],
					m = b[2],
					h = b[3],
					p = b[4],
					j = b[5],
					k = b[6],
					l = b[7],
					n = 0;
				64 > n;
				n++
			) {
				if (16 > n) {
					a[n] = c[d + n] | 0;
				}
				else {
					var r = a[n - 15];
					var g = a[n - 2];
					a[n] =
						(((r << 25) | (r >>> 7)) ^
							((r << 14) | (r >>> 18)) ^
							(r >>> 3)) +
						a[n - 7] +
						(((g << 15) | (g >>> 17)) ^
							((g << 13) | (g >>> 19)) ^
							(g >>> 10)) +
						a[n - 16];
				}
				r =
					l +
					(((p << 26) | (p >>> 6)) ^
						((p << 21) | (p >>> 11)) ^
						((p << 7) | (p >>> 25))) +
					((p & j) ^ (~p & k)) +
					q[n] +
					a[n];
				g =
					(((e << 30) | (e >>> 2)) ^
						((e << 19) | (e >>> 13)) ^
						((e << 10) | (e >>> 22))) +
					((e & f) ^ (e & m) ^ (f & m));
				l = k;
				k = j;
				j = p;
				p = (h + r) | 0;
				h = m;
				m = f;
				f = e;
				e = (r + g) | 0;
			}
			b[0] = (b[0] + e) | 0;
			b[1] = (b[1] + f) | 0;
			b[2] = (b[2] + m) | 0;
			b[3] = (b[3] + h) | 0;
			b[4] = (b[4] + p) | 0;
			b[5] = (b[5] + j) | 0;
			b[6] = (b[6] + k) | 0;
			b[7] = (b[7] + l) | 0;
		},
		_doFinalize() {
			const a = this._data;
			const d = a.words;
			const b = 8 * this._nDataBytes;
			const e = 8 * a.sigBytes;
			d[e >>> 5] |= 128 << (24 - (e % 32));
			d[(((e + 64) >>> 9) << 4) + 14] = h.floor(b / 4294967296);
			d[(((e + 64) >>> 9) << 4) + 15] = b;
			a.sigBytes = 4 * d.length;
			this._process();

			return this._hash;
		},
		clone() {
			const a = g.clone.call(this);
			a._hash = this._hash.clone();

			return a;
		},
	}));
	s.SHA256 = g._createHelper(f);
	s.HmacSHA256 = g._createHmacHelper(f);
})(Math);
