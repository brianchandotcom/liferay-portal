import ErrorPage from 'shared/pages/ErrorPage';
import React from 'react';

/**
 * Root `errorElement` for the data router. Renders `ErrorPage` for an explicit
 * 404 (the catch-all route) and for any uncaught render-time error thrown by a
 * route element.
 */
const ErrorBoundary = () => <ErrorPage />;

export default ErrorBoundary;
