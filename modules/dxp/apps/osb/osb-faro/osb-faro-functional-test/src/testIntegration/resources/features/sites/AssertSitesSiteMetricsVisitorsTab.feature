@spira_Sites @Sites @team_FARO @priority_3 @blocked
Feature: Assert Site Metrics select Visitors card tab
	As a Business User, I should be able to select Visitors card tab in Site Metrics

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert Site Metrics Visitors card tab
		Given I go to the "Sites" page
		When I click the "Visitors" button
		Then I should see mouse over header "Visitors" for "Site Metrics" table in row "1"