@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Acquisition card Referrers highest session
	As a Business User, I should be able to view Referrers highest session in Sites

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert Acquisition Referrers highest session
		Given I go to the "Sites" page
		When I click the "Referrers" button
		Then I should see the Referrers named "www.dino-boyer.com" in the table