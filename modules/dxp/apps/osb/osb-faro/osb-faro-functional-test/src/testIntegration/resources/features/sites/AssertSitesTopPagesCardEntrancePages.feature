@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Top Pages card Entrance Pages highest entrances
	As a Business User, I should be able to view highest entrances pages in Sites

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert sites Entrance Pages highest entrance
		Given I go to the "Sites" page
		When I click the "Entrance Pages" button
		Then I should see an entrance page named "innovate dot-com methodologies" in the table