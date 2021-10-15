const sassRegex = /\.(scss|sass)$/;

module.exports = {
  babel: {
    plugins: [
      [
        "babel-plugin-root-import",
        {
          paths: [
            {
              rootPathSuffix: "./src",
              rootPathPrefix: "~/",
            },
            {
              rootPathSuffix: "./src/shared",
              rootPathPrefix: "~/shared",
            },
            {
              rootPathSuffix: "./src/apps",
              rootPathPrefix: "~/apps",
            },
          ],
        },
      ],
    ],
  },
  webpack: {
    configure: (webpackConfig, { env, paths }) => {
      /**
       * This change is necessary to import SCSS as string
       * to inject into style tag
       */

      webpackConfig.module.rules[1].oneOf.unshift({
        test: sassRegex,
        exclude: /node_modules/,
        use: [
          "sass-to-string",
          {
            loader: "sass-loader",
            options: {
              sassOptions: {
                outputStyle: "compressed",
              },
            },
          },
        ],
      });

      /**
       * Avoid hashes in filenames
       */

      webpackConfig.output.chunkFilename = "static/js/[name].js";
      webpackConfig.output.filename = "static/js/[name].js";

      return webpackConfig;
    },
  },
};
