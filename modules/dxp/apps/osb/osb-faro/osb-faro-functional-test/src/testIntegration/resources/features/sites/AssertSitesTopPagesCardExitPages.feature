@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Top Pages card Exit Pages highest exits
	As a Business User, I should be able to view highest exit pages in Sites

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert sites Exit Pages highest exit
		Given I go to the "Sites" page
		When I click the "Exit Pages" button
		Then I should see an exit page named "innovate dot-com methodologies" in the table