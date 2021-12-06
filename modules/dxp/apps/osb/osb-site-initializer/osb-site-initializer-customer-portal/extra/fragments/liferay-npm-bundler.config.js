/* eslint-disable @liferay/imports-first */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const fs = require('fs')

const path = require('path');;

const SOURCE_DIR = 'src';

module.exports = {
  'create-jar': false,
  "exports": fs.readdirSync(SOURCE_DIR).reduce(
    (exportsObject, exportFragment) => ({
      ...exportsObject,
      [exportFragment]: `.${path.sep}${exportFragment}${path.sep}index.js`,
    }),
    {}
  ),
  "imports": { __REACT_PROVIDER__: { react: '*' } },
  "output": `build${path.sep}liferay-npm-bundler-output`,
  "source": SOURCE_DIR,
  "webpack": {
    mode: 'production',
    module: {
      rules: [
        {
          exclude: /node_modules/,
          test: /\.js$/,
          use: [
            {
              loader: 'babel-loader',
              options: {
                presets: ['@babel/preset-env', '@babel/preset-react'],
              },
            },
          ],
        },
      ]
    }
  },
  "workdir": `build${path.sep}liferay-npm-bundler-workdir`
};
