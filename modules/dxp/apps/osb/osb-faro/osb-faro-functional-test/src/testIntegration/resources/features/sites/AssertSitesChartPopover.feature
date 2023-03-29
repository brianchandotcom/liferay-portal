@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Site Activities Chart Popover follows format
	As a Business User, I should be able to see Site Activities chart popover

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Sites Activity Chart Popover follows format
		Given I go to the "Sites" page
		And I click the "Last 30 days" dropdown in the "LIFERAY-DATASOURCE-FARO-EXAMPLE Activities" card
		And I click the "Custom Range" button
		And I set start date "July 1 2020" and end date "July 13 2020"
		When I mouse over row "1" in "LIFERAY-DATASOURCE-FARO-EXAMPLE Activities" card
		Then I should see chart popover formatted YYYY MMM DD